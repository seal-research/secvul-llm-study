import os
import argparse
os.environ["HF_HOME"]="~/common-data/XXXX-2/hf_cache"
from utils.mylogger import MyLogger
from utils.utils import (
    store_results,
    parse_llm_results,
    compute_results,
    compute_precision_recall_accuracy,
    compute_prec_recall_multiclass,
)
from data.prompt import PROMPTS, PROMPTS_SYSTEM, PROMPT_TYPES
import pandas as pd
from tqdm.contrib.concurrent import thread_map

OUTPUT_DIR = 'shared/v2/study_results_v2/'

def is_too_large(model, prompt, max_input_tokens):    
    l=len(model.tokenizer.tokenize(prompt))
    print(f"Prompt length:{l}")
    return l > max_input_tokens

def run_exp(model_name, benchmark, **kwargs):
    import time

    timestamp = int(time.time())
    exp_st_time = time.time()
    overwrite = kwargs.get("overwrite", False)
    output_dir = kwargs["output_dir"]
    if kwargs.get('adv', None) is not None:
        output_folder = "{}/{}_{}_{}_adv-{}".format(
            output_dir,
            model_name,
            benchmark,
            "prompt-{}_user-{}_system-{}".format(kwargs["prompting_technique"], kwargs["prompt_type"], kwargs["system_prompt_type"]),
            kwargs['adv']
        )
    else:
        output_folder = "{}/{}_{}_{}".format(
            output_dir,
            model_name,
            benchmark,
            "prompt-{}_user-{}_system-{}".format(kwargs["prompting_technique"], kwargs["prompt_type"], kwargs["system_prompt_type"])          
            #timestamp,
        )
    if os.path.exists(output_folder):
        print("Output folder already exists!!", output_folder)
        #exit(1)
    
    os.makedirs(output_folder, exist_ok=True)
    logger = MyLogger(os.path.join(output_folder, "log.txt"))

    logger.log("Output folder: {}".format(output_folder))
    logger.log("Model name: {}".format(model_name))
    logger.log("Benchmark: {}".format(benchmark))

    if kwargs["reload"] is not None:
        arguments = "argument_reload.txt"
    else:
        arguments = "argument.txt"
    with open(os.path.join(output_folder, arguments), "a") as f:
        f.write("Model:{}\n".format(model_name))
        f.write("Dataset:{}\n".format(benchmark))
        for k in kwargs:
            f.write("{}:{}\n".format(k, str(kwargs[k])))
            
    #kwargs['logger'] = logger    
    data = get_data(benchmark, kwargs, logger)

    from models.llm import LLM   
    model = None

    logger.log(">>Data Items Selected: {}".format(len(data.df)))
    processed_samples=0
    for i in data.iterator:
        item = data.get_items(i)
        
        snippet = item[-1]  # last item is the code snippet
        prompt_cwe = item[1]
        cwenames = pd.read_csv("utils/cwenames_top25.txt", index_col="id")
       
        if not model_name.lower().startswith("gpt"):
            query = PROMPTS[kwargs["prompt_type"]].format(snippet, "{} (CWE-{})".format(cwenames.loc[int(prompt_cwe)]['name'], prompt_cwe))
            system_prompt = PROMPTS_SYSTEM[kwargs["system_prompt_type"]]
            model_input = [{"role": "system", "content": system_prompt}, {"role": "user", "content": query}]
        else:
            model_input = {
                "id": str(item[0]),
                "snippet": snippet,
                "prompt_cwe": prompt_cwe
            }

        st = time.time()
        result_file=os.path.join(output_folder, str(item[0]), "result.json")
        isnull = False
        if os.path.exists(result_file):
            import json
            isnull = json.load(open(result_file))['llm_label_raw'] is None
            print("null", isnull)
        #continue
        if (os.path.exists(os.path.join(output_folder, str(item[0]), "pred.txt"))
            and os.path.getsize(os.path.join(output_folder, str(item[0]), "pred.txt")) > 0 and (not overwrite) and (not isnull)):
            logger.log("Skipping ID: " + str(item[0]))
            processed_samples+=1
            continue
        else:
            if model is None:
                model = LLM.get_llm(model_name, kwargs, logger)
            if "gpt" not in model_name.lower():
                if is_too_large(model, snippet, kwargs.get("max_input_tokens", 16000)):
                    logger.log("Too large, skipping")
                    continue            
            pred = model.predict(model_input)
            time_taken = time.time() - st
            logger.log(os.path.join(output_folder, str(item[0])))
            logger.log("ID: " + str(item[0]))
            logger.log("CWE: " + str(item[1]))
            logger.log("Label: " + str(item[2]))
            logger.log(f"Prediction: {pred}")
            logger.log(f"Time taken: {time_taken}")
            logger.log("\n ---------------------------- \n")

            store_results(
                output_folder,
                str(item[0]),
                {
                    "query": snippet,
                    "pred": pred,
                    "cwe": str(item[1]),
                    "label": str(item[2]),
                    "time": time_taken,
                },
            )
        
        if kwargs.get('max_samples', None) is not None and processed_samples >= kwargs['max_samples']:
            logger.log(">>Max samples reached!! :: " + str(kwargs['max_samples']))
            break
        processed_samples+=1
    
    exp_time_taken = time.time() - exp_st_time
    with open(os.path.join(output_folder, "time_taken.txt"), "w") as f:
        f.write(str(exp_time_taken))

    logger.log("Experiment time taken: {}".format(exp_time_taken))
    logger.log("Computing Results...")
    results = compute_results(output_folder)

    df = pd.DataFrame.from_dict(results, orient="index")
    df.to_csv(os.path.join(output_folder, "results.csv"))

    prec_recall = compute_precision_recall_accuracy(df, "true_label", "llm_label")
    # print results
    logger.log(">>Total samples: " + str(len(df)))
    logger.log(">>Total vulnerable: " + str(len(df[df["true_label"] == True])))
    logger.log(">>Total not vulnerable: " + str(len(df[df["true_label"] == False])))

    logger.log(">>Accuracy: " + str(prec_recall["accuracy"]))
    logger.log(">>Recall: " + str(prec_recall["recall"]))
    logger.log(">>Precision: " + str(prec_recall["precision"]))

    logger.log(">>Total correct CWE: " + str(len(df[df["cwe_correct"] == True])))
    logger.log(
        ">>Total correct CWE and Label: "
        + str(len(df[(df["cwe_correct"] == True) & (df["correct"] == True)]))
    )

    # cwe specific results
    precision_dict, recall_dict, accuracy_dict = compute_prec_recall_multiclass(
        df, "true_cwe", "llm_cwe"
    )
    for k in precision_dict.keys():
        logger.log(
            ">>CWE: "
            + str(k)
            + ",Precision: "
            + str(precision_dict[k])
            + ",Recall: "
            + str(recall_dict[k])
            + ",Accuracy: "
            + str(accuracy_dict[k])
        )

    return output_folder

def get_data(benchmark, kwargs, logger):
    if benchmark.startswith("owasp"):
        from data.owasp import OWASP

        data = OWASP(logger, **kwargs)
    elif benchmark.startswith("bigvul"):
        from data.bigvul import BigVul

        data = BigVul(benchmark, logger, **kwargs)
    elif benchmark.startswith("ossf"):
        from data.ossf import OSSFJS

        data = OSSFJS(logger, **kwargs)
    elif benchmark.startswith("devign"):
        from data.devign import Devign

        data = Devign(logger, **kwargs)
    elif benchmark.startswith("cvefixes"):
        from data.cvefixes import CVEFixes

        data = CVEFixes(benchmark, logger,  **kwargs)
    elif benchmark.startswith("juliet"):
        from data.juliet import Juliet

        data = Juliet(benchmark, logger,  **kwargs)
    elif benchmark.startswith("stonesoup"):
        from data.stonesoup import Stonesoup

        data = Stonesoup(benchmark, logger, **kwargs)
    else:
        logger.log(benchmark + " not implemented")
        exit(1)    
    return data


if __name__ == "__main__":
    argparse = argparse.ArgumentParser()
    argparse.add_argument("--model_name", type=str, default="gpt-4")
    argparse.add_argument("--output_dir", type=str, default=OUTPUT_DIR)

    # prompt parameters
    argparse.add_argument("--prompting_technique", type=str, choices=PROMPT_TYPES,  default="basic", help="Prompting technique to use. Defaults to a basic prompt with a system and a user message.")
    argparse.add_argument("--prompt", type=str, default="generic", help="User prompt to use")
    argparse.add_argument("--sys_prompt", type=str, default="generic")


    argparse.add_argument("--bits", type=int, required=False, help="Number of bits to use for quantization")
    argparse.add_argument("--flash", action="store_true", help="Enable flash attention")
    argparse.add_argument("--max_input_tokens", type=int, default=16000, help="Max Input Size for LLM; Skipping inputs larger than this")
    

    # dataset parameters
    argparse.add_argument("--benchmark", type=str, default="owasp")
    argparse.add_argument("--n_examples",type=int, help="Number of examples per CWE", required=False, default=None)
    argparse.add_argument("--top_cwe", type=int, help="Only Top K CWE", default=None, required=False)
    argparse.add_argument("--vul", help="Only vulnerable examples", default=None, required=False)
    argparse.add_argument("--loc", help="Filter by loc (only supported for Juliet for now)", default=None, required=False)
    argparse.add_argument("--sort", help="Sort by loc (only supported for Juliet for now)", default=None, required=False, 
                          choices=['random', 'cwe', 'random-cwe'])   
    argparse.add_argument("--max_samples", help="Max samples to use", default=None, required=False, type=int)

    argparse.add_argument("--reload", help="Reload from directory", default=None, required=False)

    argparse.add_argument("--openai_api_key", default=None, type=str, help="OpenAI API Key. Taken from env if not specified")

    argparse.add_argument("--cves_to_ignore", default=None, type=str, help="Path to txt file with CVE IDs to ignore")

    argparse.add_argument("--validate_results_from_dir", default=None, type=str, help="Path to results directory whose results need to be validated by GPTx (self reflection). Reloads the prompts and predictions from the dir.")

    argparse.add_argument("--indices", default=None, type=str, help="Indices to filter by")

    argparse.add_argument("--overwrite", action='store_true')
    
    argparse.add_argument("--adv", default=None, type=str, help="Run adversarial experiment", choices=["deadcode", "varname", "dummybranch"])
    
    argparse.add_argument("--adv_ref", default=None, type=str, help="Use correct ids from this folder")
    
    argparse.add_argument("--adv_num", default=None, type=int, help="Number of adversarial samples to generate")
    

    # TODO: add more args as necessary
    args = argparse.parse_args()
    kwargs = dict()
    kwargs["output_dir"] = args.output_dir
    kwargs["prompt_type"] = args.prompt
    kwargs["system_prompt_type"] = args.sys_prompt
    kwargs["prompting_technique"] = args.prompting_technique

    kwargs["bits"] = args.bits
    kwargs["flash"] = args.flash
    kwargs["max_input_tokens"] = args.max_input_tokens

    kwargs["n_examples"] = args.n_examples
    kwargs["top_cwe"] = args.top_cwe
    kwargs["vul"] = args.vul
    kwargs["loc"] = args.loc
    kwargs["sort"] = args.sort
    kwargs["max_samples"] = args.max_samples

    kwargs["reload"] = args.reload

    kwargs["cves_to_ignore"] = args.cves_to_ignore

    # OpenAI specific kwargs
    kwargs["openai_api_key"] = args.openai_api_key
    kwargs["validate_results_from_dir"] = args.validate_results_from_dir

    kwargs["indices"] = args.indices

    kwargs["overwrite"] = args.overwrite
    
    kwargs["adv"] = args.adv
    kwargs["adv_ref"] = args.adv_ref
    kwargs["adv_num"] = args.adv_num
    
    assert not (kwargs.get("max_samples", None) and kwargs.get("indices", None)), "Both max samples and indices cannot be enabled. Use only one!"

    run_exp(args.model_name, args.benchmark, **kwargs)
