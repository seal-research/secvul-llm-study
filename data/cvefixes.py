import pandas as pd
import os
import models.config as config
import re 

class CVEFixes:
    def __init__(self, data_name, logger, **kwargs):
        self.data_name = data_name
        self.logger = logger

        if self.data_name == "cvefixes-java-method":
            self.csv_file = os.path.join(config.config['DATA_DIR_PATH'],"CVEFixes_v1.0.7", "cvefixed_java_method.csv")        
        elif self.data_name == 'cvefixes-c-cpp-method': # both c and cpp samples
            self.csv_file = os.path.join(config.config['DATA_DIR_PATH'],"CVEFixes_v1.0.7", "cvefixed_c_cpp_method.csv")
    
        
        self.kwargs = kwargs
        self.logger = logger
        self.df = None
        self.iterator = self._read_cvefixes()
    
    def _read_cvefixes(self):
        
        f=open(self.csv_file)
        df = pd.read_csv(f, delimiter=',', quotechar='"', header=0)
        # select only vulnerable
        #print(df.columns)
        #df=df[df['vul'] == 1]
        if self.kwargs.get('indices', None) is not None:
            df=df.loc[self.kwargs['indices']]
            self.logger.log("Read {} rows from indices".format(len(df)))
            self.df = df
            return df.iterrows()
        
        if 'vul' in self.kwargs and self.kwargs['vul'] is not None:
            from utils.utils import is_true
            if is_true(self.kwargs['vul']):
                df=df[df['vul'] == True]
            else:
                df=df[df['vul'] == False]
        if self.kwargs.get('top_cwe', None) is not None:
            top_cwes = ['CWE-' + k for k in open("utils/cwe_top_25.txt").read().strip().splitlines()[:int(self.kwargs['top_cwe'])]]
            df = df[df['cwe_id'].isin(top_cwes)]

        # sorting after cwe selection, but before n_examples to allow both positive and negative examples
        if self.kwargs.get('sort', None) == 'random':
            df=df.sample(frac=1, random_state=1)
        elif self.kwargs.get('sort', None) == 'cwe': # sort by cwe
            df=df.sort_values(by='cwe_id', ascending=True)
        elif self.kwargs.get('sort', None) == 'random-cwe':
            df=df.sample(frac=1, random_state=1)
            df=df.sort_values(by='cwe_id', ascending=True)

        if self.kwargs.get('n_examples', None) is not None:
            if self.kwargs['top_cwe'] is not None:
                df = df.groupby('cwe_id').head(int(self.kwargs['n_examples']))
            else:
                df = df.head(int(self.kwargs['n_examples']))
        self.df = df
        
        ########
        # print("WARNINING!!!!!! Before", len(df))
        # df["words"]=df["code"].apply(lambda x: len(str(x).split()))
        # self.df = df[df.words < 500]
        # print("After", len(self.df))
        ##########

        return df.iterrows()
    
    def get_items(self, n):
        try: 
            cwe_id = n[1]['cwe_id'].split("-")[-1]
        except:
            # To handle Nan or invalid CWE IDs
            cwe_id = "-1"
        if 'python' in self.data_name:
            return n[0], cwe_id, n[1]['vul'], self.remove_comments_python(str(n[1]['code']))
        elif 'java' in self.data_name:
            return n[0], cwe_id, n[1]['vul'], self.remove_comments_java(str(n[1]['code']))
        elif 'cvefixes-c' in self.data_name:
            return n[0], cwe_id, n[1]['vul'], self.remove_comments_cpp(str(n[1]['code']))

    def remove_comments_python(self, code):
        code = re.sub(re.compile("#.*?\n" ) ,"" ,code)     
        return code
    
    def remove_comments_cpp(self, code):        
        code = re.sub(re.compile("/\*.*?\*/",re.DOTALL ) ,"" , code)
        code = re.sub(re.compile("[^:]//.*?\n|^//.*?\n" ) ,"" , code)
        return code

    def remove_comments_java(self, code):
        code = re.sub(re.compile("/\*.*?\*/",re.DOTALL ) ,"" ,code)
        code = re.sub(re.compile("[^:]//.*?\n|^//.*?\n" ) ,"" ,code)
        return code

