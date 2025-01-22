import os
import pandas as pd
import models.config as config
from utils.utils import is_true
from utils.mylogger import MyLogger

cwe_names = [
    (78, "OS Command Injection"),
    (327, "Cryptographic"),
    (328, "weak hash"),
    (90, "LDAP Injection"),
    (22, "Path Traversal"),
    (89, "SQL Injection"),
    (501, "trust boundary violation"),
    (330, "Insufficiently Random Values"),
    (643, "XPath Injection"),
    (79, "Cross Site Scripting"),
    (125, "Out-of-bounds Read"),
    (362, "Race Condition or Concurrent Execution using Shared Resource with Improper Synchronization"),
    (77, "Command Injection"),
    (787, "Out-of-bounds Write"),
    (416, "Use After Free"),
    (20, "Improper Input Validation"),
    (269, "Improper Privilege Management"),
    (476, "NULL Pointer Dereference"),
    (190, "Integer Overflow"),
    (94, "Improper Control of Generation of Code or Code Injection"),
    (352, "Cross-Site Request Forgery"),
    (862, "Missing Authorization"),
    (918, "Server-Side Request Forgery (SSRF)"),
    (119, "Improper Restriction of Operations within the Bounds of a Memory Buffer"),
    (502, "Deserialization of Untrusted Data"),
    (287, "Improper Authentication"),
    (434, "Unrestricted Upload of File with Dangerous Type"),
    (798, "Use of Hard-coded Credentials"),
    (306, "Missing Authentication for Critical Function"),
    (863, "Incorrect Authorization"),
    (276, "Incorrect Default Permissions"),
    (614, "Sensitive Cookie in HTTPS Session Without 'Secure' Attribute"),
]

cwe_name_mapping = {key: val for (key, val) in cwe_names}

class Juliet:
    def __init__(self, data_name, logger, **kwargs):
        self.data_name = data_name
        if self.data_name == "juliet-java-1.3":
            self.csv_file = os.path.join(config.config['DATA_DIR_PATH'],"juliet", "juliet-java-1.3.csv")
        elif self.data_name == 'juliet-cpp-1.3':
            self.csv_file = os.path.join(config.config['DATA_DIR_PATH'],"juliet", "juliet-cpp-1.3.csv")
        self.kwargs = kwargs
        self.df = None
        self.logger = logger
        self.iterator = self._read_csv()

    def _read_csv(self):
        f=open(self.csv_file)
        df = pd.read_csv(f, delimiter=',', quotechar='"', header=0)
        if self.kwargs.get('indices', None) is not None:
            df=df.loc[self.kwargs['indices']]
            self.logger.log("Read {} rows from indices".format(len(df)))
            self.df = df
            return df.iterrows()

        if 'vul' in self.kwargs and self.kwargs['vul'] is not None:
            if is_true(self.kwargs['vul']):
                df=df[df['vul'] == True]
            else:
                df=df[df['vul'] == False]
        if self.kwargs.get('top_cwe', None) is not None:
            top_cwes = ['CWE' + k for k in open("utils/cwe_top_25.txt").read().strip().splitlines()[:int(self.kwargs['top_cwe'])]]
            df = df[df['cwe'].isin(top_cwes)]

        # sorting after cwe selection, but before n_examples to allow both positive and negative examples
        if self.kwargs.get('sort', None) == 'random':
            df=df.sample(frac=1, random_state=1)
        elif self.kwargs.get('sort', None) == 'cwe': # sort by cwe
            df=df.sort_values(by='cwe', ascending=True)
        elif self.kwargs.get('sort', None) == 'random-cwe':
            df=df.sample(frac=1, random_state=1)
            df=df.sort_values(by='cwe', ascending=True)


        if self.kwargs.get('n_examples', None) is not None:
            if self.kwargs['top_cwe'] is not None:
                df = df.groupby('cwe').head(int(self.kwargs['n_examples']))
            else:
                df = df.head(int(self.kwargs['n_examples']))

        # read code snippets and filter by loc
        df['code']=df['file'].map(lambda x: self.get_code(x))
        df['sloc']=df['code'].map(lambda x: self.get_sloc(x))
        df['cwe_name']=df['cwe'].map(lambda x: self.get_cwe_name(x))
        if self.kwargs.get('loc', None) is not None:
            df = df[df['sloc'] >= int(self.kwargs['loc'])]
        self.df = df
        return df.iterrows()

    def get_sloc(self, code):
        lines=code.splitlines()
        return len([l for l in lines if len(l.strip()) > 0 and not l.strip().startswith("import ") and not l.strip() in ['}', '{']])

    def get_code(self, path):
        if self.data_name == "juliet-cpp-1.3":
            return open(os.path.join(config.config['DATA_DIR_PATH'],"juliet", "cpp-1.3", path)).read()
        elif self.data_name == "juliet-java-1.3":
            return open(os.path.join(config.config['DATA_DIR_PATH'],"juliet", "java-1.3", path)).read()

    def get_cwe_name(self, cwe: str):
        id = int(cwe[3:])
        return cwe_name_mapping[id]

    def get_items(self, n):
        return n[0], n[1]['cwe'].replace("CWE", ""), n[1]['vul'], self.get_code(n[1]['file'])

