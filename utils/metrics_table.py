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

prompt_type_map={
    ('simple','generic'): '\\generic',
    ('generic', 'generic'): '\\generic',
    ('simple', 'cwe_specific'): '\\cwespecific',
    ('generic', 'cwe_specific'): '\\cwespecific',
    ('dataflow_steps', 'cwe_specific'): '\\df'
}

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


def process(main_dir, lang, common):
    if common:
        print(">>Filtering by common indices")
    all_results=dict()
    common_indices = dict()
    for d in glob(main_dir+"/*/*", recursive=True):
        if not os.path.isdir(d):
            continue
        # if 'ash08' not in d:
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
        # if 'juliet-cpp-1.3' in d:
        #     df=filter(df, indices='results/juliet-cpp-1.3-indices-2k.txt')
        # elif 'juliet-java-1.3' in d:
        #     #df=filter(df, indices='results/juliet-java-1.3-indices-2k.txt')
        #     #df=filter(df, indices='results/codellama-13b-instruct_juliet-java-1.3_prompt-basic_user-generic_system-simple_eg-None_cwe-25_vul-None_bits-None_1697254891_final_indices.txt')
        #     df=filter(df, indices='results/codellama-7b-instruct_juliet-java-1.3_prompt-basic_user-cwe_specific_system-dataflow_steps_eg-None_cwe-25_vul-None_bits-None_1697330193_final_indices.txt')
        # elif 'cvefixes-c-cpp' in d:
        #     df=filter(df, indices='results/codellama-13b-instruct_cvefixes-c-cpp-method_prompt-basic_user-cwe_specific_system-simple_eg-None_cwe-25_vul-None_bits-None_1697160984_final_indices.txt')
        #     #df=filter(df, max_samples=2000)        
        # elif 'cvefixes-java' in d:
        #     #df=filter(df, indices='results/codellama-13b-instruct_cvefixes-java-method_prompt-basic_user-cwe_specific_system-simple_eg-None_cwe-25_vul-None_bits-None_1697161466_final_indices.txt')
        #     df=filter(df, indices='results/codellama-7b-instruct_cvefixes-java-method_prompt-basic_user-cwe_specific_system-dataflow_steps_eg-None_cwe-25_vul-None_bits-None_1697330066_final_indices.txt')
        #     #df=filter(df, max_samples=2000)  
        # elif 'owasp' in d:
        #     df=filter(df, indices='results/codellama-7b-instruct_owasp_prompt-basic_user-cwe_specific_system-dataflow_steps_eg-None_cwe-25_vul-None_bits-None_1697331297_final_indices.txt')
        # else:
        
        df=filter(df)

        missing_labels=df[df['llm_label_raw'].isnull()]
        missing_cwes=df[df['llm_cwe_raw'].isnull()]
        
        print("!!Missing labels: {}/{}".format(len(missing_labels), len(df)))
        print("!!Missing cwes: {}/{}".format(len(missing_cwes), len(df)))
        
        ##################
        if len(missing_labels) > 0:
            print("Removing null")
            df=df[~df['llm_label_raw'].isnull()]
            # with open(os.path.join("results", os.path.basename(d)+"_missing_indices.txt"), "w") as f:
            #     for k in missing_labels.index:
            #         print(k, file=f)
            # with open(os.path.join("results", os.path.basename(d)+"_final_indices.txt"), "w") as f:
            #     for k in df.index:
            #         print(k, file=f)
        ##################

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
        all_results[d]=[df, args]
    if common:
        all_datasets =[all_results[k][1]['Dataset'] for k in all_results]
        for ds in all_datasets:
            all_indices=[list(all_results[k][0].index) for k in all_results if all_results[k][1]['Dataset'] == ds]
            common_indices[ds]=list(set(all_indices[0]).intersection(*all_indices[1:]))
        # filtering indices
        for ds in all_datasets:
            for k in all_results:
                if all_results[k][1]['Dataset'] == ds:
                    all_results[k][0] = all_results[k][0].loc[common_indices[ds]]
        
    return all_results

def gen_table(all_results_df, lang):
    model_names=[all_results_df[k][1]['Model'] for k in all_results_df]
    model_seq=['gpt-4', 'gpt-3.5', 'codellama-13b-instruct', 'codellama-7b-instruct']
    prompt_seq=[('simple','generic'), ('generic', 'generic'), 
                ('simple', 'cwe_specific'), ('generic', 'cwe_specific'), ('dataflow_steps', 'cwe_specific')]
    dataset_seq = ['owasp', 'juliet-java-1.3', 'cvefixes-java-method', 'juliet-cpp-1.3', 'cvefixes-c-cpp-method']
    if lang == "java":
        dataset_seq = ['owasp', 'juliet-java-1.3', 'cvefixes-java-method']
    if lang == "cpp":
        dataset_seq = ['juliet-cpp-1.3', 'cvefixes-c-cpp-method']
    entries=[]
    headers=["Model", "Prompt"]   
    for d in dataset_seq:
        headers.extend(["{}".format(dataset_map[d]), "", "", ""])
    metrics_header=["", ""]
    metrics_header.extend(["C", "Acc", "P", "R", "F1"]*len(dataset_seq))
    entries.append(metrics_header)

    for prompt in prompt_seq:
        for m in model_seq:              
            entry=[]
            entry.append(model_name_map[m])
            entry.append(prompt_type_map[prompt])
            for data in dataset_seq:
                res=[all_results_df[k][0] for k in all_results_df 
                     if all_results_df[k][1]['Model'] == m 
                     and all_results_df[k][1]['prompt_type'] == prompt[1] 
                     and all_results_df[k][1]['system_prompt_type'] == prompt[0] 
                     and all_results_df[k][1]['Dataset'] == data]
                assert len(res)<=1, (m, data, prompt)
                if len(res) == 0:
                    entry.append('-')
                    entry.append('-')
                    entry.append('-')
                    entry.append('-')
                    entry.append('-')
                else:
                    df=res[0]
                    metrics= compute_precision_recall_accuracy(df, "true_label", "llm_label")
                    entry.append(len(df))
                    entry.append(format(metrics['accuracy'], '.2f'))
                    entry.append(format(metrics['precision'], '.2f'))
                    entry.append(format(metrics['recall'], '.2f'))
                    entry.append(format(metrics['F1'], '.2f'))
            if entry.count('-') == len(entry) - 2:
                continue
            
            entries.append(entry)
    return entries, headers


if __name__ == "__main__":
    #python utils/metrics_table.py java|cpp [filter by indices: 1|0]
    all_results_df = process('./results', sys.argv[1], int(sys.argv[2]) == 1) # skip for gpt
    entries, headers = gen_table(all_results_df, sys.argv[1])
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
