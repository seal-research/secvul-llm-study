import os
import pandas as pd

cwe_to_title_mapping = {
    79: "XSS (Cross-Site Scripting)",
    89: "SQL Injection",
    78: "OS Command Injection",
    22: "Path Traversal",
}


def store_results(experiment_output_dir, id, results):
    id_results_dir = os.path.join(experiment_output_dir, id)
    os.makedirs(id_results_dir)
    for result in results.keys():
        with open(os.path.join(id_results_dir, str(result) + ".txt"), "w") as f:
            f.write(str(results[result]))
            f.write("\n")


def parse_llm_results(pred_text):
    results = dict()
    import re
    pred_text=re.sub(r'\\text\{([^}]*)\}', r'\1', pred_text, re.IGNORECASE)
    pred_text=re.sub(r'\\textbf\{([^}]*)\}', r'\1', pred_text, re.IGNORECASE)
    vul = re.findall(
        "vulnerability\s*[:=]\s*(YES|NO|Y|N|NA|N/A)", pred_text, re.IGNORECASE
    )
    results["vulnerability"] = vul[0] if len(vul) > 0 else None

    vul_type = re.findall("type\s*[:=]\s*(CWE[-_]\d+|NA|N/A)", pred_text, re.IGNORECASE)
    results["vulnerability type"] = vul_type[0] if len(vul_type) > 0 else None

    vul_name = re.findall("name\s*[:=]\s*([^|]*)", pred_text, re.IGNORECASE)
    results["vulnerability name"] = vul_name[0] if len(vul_name) > 0 else None

    if (
        results["vulnerability type"] is None
        and results["vulnerability name"] is not None
    ):
        vul_type = re.findall(
            "(CWE[-_]\d+|NA|N/A)", results["vulnerability name"], re.IGNORECASE
        )
        vul_type = vul_type[0] if len(vul_type) > 0 else None
        results["vulnerability type"] = vul_type
    results["vulnerability type"] = (
        str(results["vulnerability type"]).split("_")[-1].split("-")[-1].strip()
    )

    loc = re.findall("lines\s*of\s*code\s*[:=]\s*([^|]*)", pred_text, re.IGNORECASE)
    results["lines of code"] = loc[0] if len(loc) > 0 else None

    exp = re.findall("explanation\s*[:=]\s*([^|]*)", pred_text, re.IGNORECASE)
    results["explanation"] = exp[0] if len(exp) > 0 else None

    return results


def parse_llm_results_old(pred_text):
    results = dict()
    import re

    try:
        res = re.findall("\$\$([^$]*)\$\$", pred_text)[0].split("|")
    except:
        res = pred_text.replace("$", "").split("|")
    for k in range(len(res)):
        r = res[k].strip().split(":")
        key = r[0].strip()
        if "type" in key:
            results[key] = (
                r[1].strip().split(",")[0].strip()
            )  # if multiple cwe types are predicted, take the first one
        else:
            try:
                results[key] = r[1].strip()
            except:
                print(pred_text)
    # print(results)
    return results


def is_true(s):
    return str(s).lower() in ["True", "true", "1", "t", "T", "y", "Y", "yes"]


def compute_prec_recall_multiclass(result: pd.DataFrame, label_col, pred_col):
    true_labels = result[label_col]
    predicted_labels = result[pred_col]
    unique_labels = true_labels.unique()

    precision_dict = {}
    recall_dict = {}
    accuracy_dict = {}

    for label in unique_labels:
        true_binary = true_labels == label
        predicted_binary = predicted_labels == label

        # Calculate True Positives (TP), False Positives (FP), and False Negatives (FN)
        TP = ((true_binary) & (predicted_binary)).sum()
        FP = ((~true_binary) & (predicted_binary)).sum()
        FN = ((true_binary) & (~predicted_binary)).sum()
        TN = ((~true_binary) & (~predicted_binary)).sum()
        # print(label, TP, FP, FN, TN)
        # Calculate Precision and Recall
        precision = (TP / (TP + FP)) if (TP + FP) > 0 else 0
        recall = (TP / (TP + FN)) if (TP + FN) > 0 else 0
        accuracy = (TP + TN) / (TP + TN + FP + FN)

        # Store Precision and Recall for the class
        precision_dict[label] = precision
        recall_dict[label] = recall
        accuracy_dict[label] = accuracy

    return precision_dict, recall_dict, accuracy_dict


def compute_precision_recall_accuracy(result: pd.DataFrame, label_col, pred_col):
    true_labels = result[label_col]
    predicted_labels = result[pred_col]
    TP = ((true_labels == 1) & (predicted_labels == 1)).sum()
    FP = ((true_labels == 0) & (predicted_labels == 1)).sum()
    FN = ((true_labels == 1) & (predicted_labels == 0)).sum()
    TN = ((true_labels == 0) & (predicted_labels == 0)).sum()
    precision = TP / (TP + FP) if TP + FP > 0 else 0
    recall = TP / (TP + FN) if TP + FN > 0 else 0
    accuracy = (TP + TN) / (TP + TN + FP + FN)
    return {
        "precision": precision,
        "recall": recall,
        "accuracy": accuracy,
        "TP": TP,
        "FP": FP,
        "FN": FN,
        "TN": TN,
        "F1": 2 * precision * recall / (precision + recall) if precision + recall > 0 else 0,
    }


def cwe_in_predicted_name(cwenames: pd.DataFrame, cwe, name):
    if name is None:
        return False
    cwe = int(cwe)

    names = (
        cwenames.loc[cwe]["name"].lower().split("|") if cwe in cwenames.index else []
    )
    if len(names) == 0:
        print(cwe, names)
    for n in names:
        if n in name.lower():
            return True
    return False
    # cwenames.loc[int(cwe)]['name'].lower() in str(llm_results['vulnerability name']).lower()


def compute_results(output_folder):
    results = dict()
    import pandas as pd

    cwenames = pd.read_csv("utils/cwenames.txt", index_col="id")

    for k in os.listdir(output_folder):
        if os.path.isdir(os.path.join(output_folder, k)):
            cur_result = dict()
            name = k

            true_label = (
                open(os.path.join(output_folder, k, "label.txt")).read().strip()
            )
            cwe = open(os.path.join(output_folder, k, "cwe.txt")).read().strip()
            time_taken = open(os.path.join(output_folder, k, "time.txt")).read().strip()

            llm_pred = open(os.path.join(output_folder, k, "pred.txt")).read().strip()
            # print(os.path.join(output_folder, k, "pred.txt"))
            try:
                llm_results = parse_llm_results(llm_pred)                
                cur_result["true_label"] = is_true(true_label)
                cur_result["true_cwe"] = cwe
                cur_result["llm_label_raw"]=llm_results["vulnerability"]
                cur_result["llm_cwe_raw"]=llm_results["vulnerability type"]

                cur_result["llm_label"] = is_true(llm_results["vulnerability"])
                cur_result["correct"] = is_true(llm_results["vulnerability"]) == is_true(true_label)

                if llm_results["vulnerability type"] == cwe or cwe_in_predicted_name(cwenames, cwe, llm_results["vulnerability name"]):
                    cur_result["llm_cwe"] = cwe
                    cur_result["cwe_correct"] = True
                else:
                    cur_result["llm_cwe"] = llm_results["vulnerability type"]
                    cur_result["cwe_correct"] = False

                # print(cur_result['llm_cwe'], cwe)
                # cur_result['cwe_correct'] =  cur_result['llm_cwe'] == cwe
                cur_result["explanation"] = llm_results["explanation"]
                cur_result["loc"] = llm_results["lines of code"]
                cur_result["time"] = time_taken
            except Exception as e:
                print("Error: ", os.path.join(output_folder, k, "pred.txt"), str(e))
                continue
            results[name] = cur_result

    return results


def group_metrics(
    results_df: pd.DataFrame,
    group_by_col: str,
    dataset_csv_path: pd.DataFrame,
    dataset_index_col: str,
) -> pd.DataFrame:
    """
    Groups metrics in results df by column in dataset
    Can be used for grouping results by commit ID, CVE, etc
    Note that dataset_index_col should map to the index of the results dataframe
    """

    results_df.index = results_df.index.astype(int)

    dataset_df = pd.read_csv(dataset_csv_path, index_col=dataset_index_col)
    assert len(dataset_df) == len(results_df)

    merged_df = results_df.join(dataset_df, how="left", validate="1:1")

    # Set datatypes can be used by the `max` method after the grouping
    merged_df.true_label = merged_df.true_label.astype(bool)
    merged_df.llm_label = merged_df.llm_label.astype(bool)
    try:
        merged_df.true_cwe = merged_df.true_cwe.astype(float)
    except:
        merged_df.true_cwe = merged_df.true_cwe.astype(str)

    grouped_df = merged_df.groupby([group_by_col])
    grouped_metrics_df = grouped_df[["true_label", "llm_label", "true_cwe"]].max()

    return grouped_metrics_df
