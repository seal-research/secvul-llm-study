import tabulate 
import os 
import sys 
from glob import glob 
import pandas as pd
from utils import compute_results, compute_precision_recall_accuracy
model_name_map={
    'gpt-4': 'GPT-4',
    'gpt-3.5': 'GPT-3.5',
    'codellama-13b-instruct': 'CodeLlama-13B',
    'codellama-7b-instruct': 'CodeLlama-7B'
}

# prompt_type_map={
#     ('simple','generic'): 'Generic',
#     ('generic', 'generic'): 'Generic',
#     ('simple', 'cwe_specific'): 'CWE-Specific',
#     ('generic', 'cwe_specific'): 'CWE-Specific',
#     ('dataflow_steps', 'cwe_specific'): 'Dataflow Steps',
#     ('generic_explanation_first', 'cwe_specific'): 'CWE-Specific (CoT)'
# }

cwe_seq = set()
top25=open('utils/cwe_top_25.txt').read().strip().split('\n')

dataset_map={
    'owasp': 'OWASP',
    'juliet-java-1.3': 'Juliet Java',
    'cvefixes-java-method': 'CVEFixes Java',
    'juliet-cpp-1.3': 'Juliet C/C++',
    'cvefixes-c-cpp-method': 'CVEFixes C/C++'

}

mapper={
        'prompt-basic_user-generic_system-simple': ('simple','generic'), 
        'prompt-basic_user-cwe_specific_system-simple': ('simple', 'cwe_specific')
        }

def gen_table(entries, lang, model):
    pass 

def filter(df, indices=None, max_samples=None):   
    top25=open('utils/cwe_top_25.txt').read().strip().split('\n')
    #print("Filtering by top 25 cwes..")        
    df=df[df['true_cwe'].isin(top25)]
    if indices is not None:
        indices = open(indices).read().strip().split('\n')
        df = df[df.index.isin(indices)]
    if max_samples:
        df=df.iloc[:max_samples]
    return df


def process(main_dir, lang):
    all_results=dict()
    for d in glob(main_dir+"/*/*", recursive=True):
        if not os.path.isdir(d):
            continue
        if "datasets" in d or "codeql" in d:
            continue
        # if "paper_results_3" not in d:
        #     continue
        # if "validated" not in d:
        #     continue
        if "paper_results_cwe_specific" not in d:
            continue

        # ## Ignore the self reflection results
        # if "validated" in d:
        #     continue

        # Only consider explanation first results
        # if "explanation_first_basic" not in d:
        #     continue
        # if 'ash07' not in d:
        #     continue
        if lang == "java":
            if not ('owasp' in d or 'juliet-java' in d or 'cvefixes-java' in d):
                continue
        if lang == "cpp":
            if not ('juliet-cpp' in d or 'cvefixes-c-cpp' in d):
                continue
        print(d)
        
        results = compute_results(d)
        df=pd.DataFrame.from_dict(results, orient="index")
        # print(df.head())
        if 'juliet-cpp-1.3' in d:
            df=filter(df, indices='results/juliet-cpp-1.3-indices-2k.txt')
        elif 'juliet-java-1.3' in d:
            df=filter(df, indices='results/juliet-java-1.3-indices-2k.txt')
        elif 'cvefixes' in d:
            df=filter(df, max_samples=2000)        
        else:
            df=filter(df)
        try:
            if os.path.exists(os.path.join(d, 'argument.txt')):
                args=open(os.path.join(d, 'argument.txt')).read().strip().split('\n')
                args=[a.split(':') for a in args]
                args={a[0]:a[1] for a in args}
            elif os.path.exists(os.path.join(d, 'argument_reload.txt')):
                args=open(os.path.join(d, 'argument_reload.txt')).read().strip().split('\n')
                args=[a.split(':') for a in args]
                args={a[0]:a[1] for a in args}
            else:
                logs=open(os.path.join(d, 'log.txt')).read().strip().split('\n')
                #User Prompt: generic
                #System Prompt: simple
                args=dict()
                args['Model']=[k for k in logs if k.startswith('Model name')][0].split(':')[1].strip()
                args['Dataset']=[k for k in logs if k.startswith('Benchmark')][0].split(':')[1].strip()
                args['prompt_type']=[k for k in logs if k.startswith('User Prompt')]
                args['system_prompt_type']= [k for k in logs if k.startswith('System Prompt')]
                if len(args['prompt_type']) == 0:
                    for k in mapper:
                        if k in d:
                            args['system_prompt_type']=mapper[k][0]
                            args['prompt_type']=mapper[k][1]
                            break
                else:
                    args['prompt_type']=args['prompt_type'][0].split(':')[1].strip()
                    args['system_prompt_type']=args['system_prompt_type'][0].split(':')[1].strip()

        except Exception as e:
            print("error:", d)
            print(e)
            continue
        all_results[d]=(df, args)
        cwe_seq.update(list(df["true_cwe"].unique()))
    return all_results

def gen_table(all_results_df, lang):
    model_names=[all_results_df[k][1]['Model'] for k in all_results_df]
    model_seq=['gpt-4', 'gpt-3.5', 'codellama-13b-instruct', 'codellama-7b-instruct']
    # prompt_seq=[('simple','generic'), 
    #             ('generic', 'generic'), 
    #             ('simple', 'cwe_specific'), 
    #             ('generic', 'cwe_specific'), 
    #             ('dataflow_steps', 'cwe_specific'),
    #             ('generic_explanation_first', 'cwe_specific')]
    dataset_seq = ['owasp', 'juliet-java-1.3', 'cvefixes-java-method', 'juliet-cpp-1.3', 'cvefixes-c-cpp-method']
    if lang == "java":
        dataset_seq = ['owasp', 'juliet-java-1.3', 'cvefixes-java-method']
    if lang == "cpp":
        dataset_seq = ['juliet-cpp-1.3', 'cvefixes-c-cpp-method']
    entries=[]
    headers=["CWE"]   
    for d in dataset_seq:
        headers.extend(["{}".format(dataset_map[d]), "", "", ""])
    metrics_header=[""]
    metrics_header.extend(["A", "P", "R", "F1"]*len(dataset_seq))
    entries.append(metrics_header)
    # for m in model_seq:       
    for cwe in top25:
        if cwe not in cwe_seq:
            continue
        entry=[]
        # entry.append(model_name_map[m])
        entry.append("CWE-"+str(cwe))
        for data in dataset_seq:
            res=[all_results_df[k][0] for k in all_results_df 
                    # if all_results_df[k][1]['Model'] == m 
                #  and all_results_df[k][1]['CWE'] == "CWE-" + str(cwe) 
                    # and all_results_df[k][1]['Dataset'] == data]
                    if all_results_df[k][1]['Dataset'] == data]
            print(cwe, data)
            assert len(res)<=1, print(res)
            if len(res) == 0:
                # entry.append('-')
                entry.append('-')
                entry.append('-')
                entry.append('-')
                entry.append('-')
            elif cwe not in res[0]["true_cwe"].unique():
                # entry.append('-')
                entry.append('-')
                entry.append('-')
                entry.append('-')
                entry.append('-')
            else:
                df=res[0]
                df=df[df["true_cwe"] == cwe]
                metrics= compute_precision_recall_accuracy(df, "true_label", "llm_label")
                # entry.append(len(df))
                entry.append(format(metrics['accuracy'], '.2f'))
                entry.append(format(metrics['precision'], '.2f'))
                entry.append(format(metrics['recall'], '.2f'))
                entry.append(format(metrics['F1'], '.2f'))
        if entry.count('-') == len(entry) - 2:
            continue
        
        entries.append(entry)
    return entries, headers


if __name__ == "__main__":
    # all_results_df = process('./results', "java")
    # entries, headers = gen_table(all_results_df, "java")
    lang = "java"
    # lang = "cpp"
    all_results_df = process('./', lang)
    entries, headers = gen_table(all_results_df, lang)

    print(
        tabulate.tabulate(
            entries,
            headers=headers,
            tablefmt="orgtbl",
            floatfmt=".2f"
        )
    )
    print(
        tabulate.tabulate(
            entries,
            headers=headers,
            tablefmt="latex_raw",
            floatfmt='.2f'
            #floatfmt=(".0f", ".0f",  ".2f", ".2f", ".2f",".2f", ".2f", ".2f",".2f", ".2f", ".2f",".2f", ".2f", ".2f",".2f", ".2f", ".2f"),
        )
    )
