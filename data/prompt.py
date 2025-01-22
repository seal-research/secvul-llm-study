PROMPTS = dict()
PROMPTS_SYSTEM = dict()

PROMPT_TYPES = [
    "basic",
    "basic_generic",
    "basic_cwe_specific",
    "self_reflection_generic",
    "self_reflection_cwe_specific",
    "instruction_cot_generic",
    "instruction_cot_cwe_specific",
    "few_shot_cot",
    "step_by_step_dataflow_analysis_generic",
    "step_by_step_dataflow_analysis_cwe_specific"
]

PROMPTS_SYSTEM["generic"] = "You are a security researcher, expert in detecting security vulnerabilities.\
Provide response only in following format: '$$ vulnerability: <YES or NO> | vulnerability type: <CWE_ID> | lines of code: <VULNERABLE_LINES_OF_CODE> | explanation: <explanation for prediction> $$'.\
Use N/A in other fields if there are no vulnerabilities. Do not include anything else in response."

PROMPTS_SYSTEM["generic_explanation_first"] = "You are a security researcher, expert in detecting security vulnerabilities.\
Provide response only in following format: '$$ explanation: <summary of the vulnerability analysis on the given code snippet> | vulnerability: <YES or NO> | vulnerability type: <CWE_ID> $$'.\
Use N/A in other fields if there are no vulnerabilities. Do not include anything else in response."

PROMPTS_SYSTEM["simple"] = "You are a security researcher, expert in detecting security vulnerabilities.\
Provide response only in following format: '$$ vulnerability: <YES or NO> | vulnerability type: <CWE_ID> | vulnerability name: <NAME_OF_CWE> | explanation: <explanation for prediction> $$'.\
Use N/A in other fields if there are no vulnerabilities. Do not include anything else in response."

PROMPTS_SYSTEM["fttest"] = "You are a security researcher, expert in detecting security vulnerabilities. Provide response only in following format:  $ vulnerability: <YES or NO> $. Use N/A in other fields if there are no vulnerabilities. Do not include anything else in response."


PROMPTS_SYSTEM["heuristics"] = '''
You are a security researcher, expert in detecting security vulnerabilities.
Carefully analyze the given code snippet and track taints from the request to response.
Here are some heuristics that might be helpful:
- A flow is tainted if at least one branch in the flow is tainted
- A flow is not tainted by dead branches (i.e., branches that will never be taken)
- Manipulations to tainted data structures may remove the taint (for example, removal of tainted elements in a data structure)
- Assume that any call to an unknown external API is tainted

Please provide a response only in the following format:
Let's think step by step:
<analysis of the given code snippet>
Verdict: $$ vulnerability: <YES or NO> | vulnerability type: <CWE_ID> | vulnerability name: <NAME_OF_CWE> | explanation: <explanation for prediction> $$'
'''

PROMPTS_SYSTEM["dataflow_steps"] = '''
You are a security researcher, expert in detecting security vulnerabilities.
Carefully analyze the given code snippet and track the data flows from various sources to sinks.
Assume that any call to an unknown external API is unsanitized.

Please provide a response only in the following format:
Here is a data flow analysis of the given code snippet:
A. Sources: 
<numbered list of input sources>
B. Sinks:
<numbered list of output sinks>
C. Sanitizers:
<numbered list of sanitizers, if any>
D. Unsanitized Data Flows:
<numbered list of data flows that are not sanitized in the format (source, sink, why this flow could be vulnerable)>
E. Vulnerability analysis verdict: $$ vulnerability: <YES or NO> | vulnerability type: <CWE_ID> | vulnerability name: <NAME_OF_CWE> | explanation: <explanation for prediction> $$'
'''

PROMPTS_SYSTEM["dataflow_steps_explanation_first"] = '''
You are a security researcher, expert in detecting security vulnerabilities.
Carefully analyze the given code snippet and track the data flows from various sources to sinks.
Assume that any call to an unknown external API is unsanitized.

Please provide a response only in the following format:
Here is a data flow analysis of the given code snippet:
A. Sources: 
<numbered list of input sources>
B. Sinks:
<numbered list of output sinks>
C. Sanitizers:
<numbered list of sanitizers, if any>
D. Unsanitized Data Flows:
<numbered list of data flows that are not sanitized in the format (source, sink, why this flow could be vulnerable)>
E. Vulnerability analysis:
<Based on this analysis, infer if the given snippet is potentially vulnerable. Give concrete reasons.>
F. Vulnerability analysis verdict: $$ vulnerability: <YES or NO> | vulnerability type: <CWE_ID> | vulnerability name: <NAME_OF_CWE>$$'
'''

PROMPTS["generic"] =\
"""
Is the following code snippet prone to any security vulnerability?

{}
"""
       



PROMPTS["zero_shot_cot"]=\
"""
Is the following code snippet prone to {1}?

{0}

Let's think step by step.
"""



PROMPTS["cpp_few_shot"]=\
"""
Code snippet:
int main(int argc, char **argv){{
    int nresp = packet_get_int();
    if (nresp > 0) {{
        response = xmalloc(nresp*sizeof(char*));
    for (i = 0; i < nresp; i++)
        response[i] = packet_get_string(NULL);
    }}
    return response[0];
}}

explanation: nresp is an integer. If nresp is a very large value, nresp*sizeof(char*) results in an overflow (because it will wrap around and result in the value 0).
Therefore, xmalloc() receives and allocates a 0-byte buffer. The following loop causes a heap buffer overflow as we write to a non-allocated memory location, which may, in turn, be used by an attacker to execute arbitrary code.
response: vulnerability: YES | vulnerability type: CWE-190  | vulnerability name: Integer Overflow

Code snippet:
void setl(char* buffer, int size) {{
    if (size >= 0 && size < 10) {{
        buffer[size] = '\0';
    }}
}}

int main() {{
    char* buffer = new char[10];
    setl(buffer, 15);
    delete[] buffer;
    return 0;
}}

explanation: buffer is a character array of size 10. The setl function takes a pointer to the array buffer and size=15 as parameters. The setl method checks whether the size is less than 10 and if yes, limits buffer to that size.
This size check in setl prevents the snippet from being prone to an Out of Bounds Write since size=15 > size of buffer.
response: vulnerability: NO | vulnerability type: N/A  | vulnerability name: N/A

Code snippet:
{0}

explanation:
"""
