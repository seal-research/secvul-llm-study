import models.config as config
import os
import pandas as pd
class OWASP:
    def __init__(self, logger, **kwargs):
        self.data_dir = os.path.join(config.config['DATA_DIR_PATH'], 'owasp_data')
        self.expected_results = os.path.join(config.config['DATA_DIR_PATH'], 'owasp_data', 'expectedresults-1.2.csv')
        self.kwargs=kwargs
        self.df = None
        self.logger = logger
        self.iterator = self.fetch_examples()
        

    def fetch_examples(self):
        # read all code snippets
        examples={ k.split(".")[0]:open(os.path.join(self.data_dir, k)).read() for k in os.listdir(self.data_dir)}

        # read all expected results
        df=pd.read_csv(self.expected_results, index_col='# test name')
        df.columns = df.columns.str.strip()

        if self.kwargs.get('indices', None) is not None:
            df=df.loc[self.kwargs['indices']]
            self.logger.log("Read {} rows from indices".format(len(df)))
            s=pd.Series(examples, name='code')
            df['code']=df.index.map(s)
            self.df = df
            return df.iterrows()
        
        if 'vul' in self.kwargs and self.kwargs['vul'] is not None:
            if self.kwargs['vul']  in ['True', 'true', '1', 't', 'T', 'y', 'Y']:
                df=df[df['real vulnerability'] == True]
            else:
                df=df[df['real vulnerability'] == False]
        
        if self.kwargs.get('top_cwe', None) is not None:
            top_cwes = [int(k.strip()) for k in open("utils/cwe_top_25.txt").read().strip().splitlines()[:int(self.kwargs['top_cwe'])]]
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
        
        # join
        s=pd.Series(examples, name='code')
        df['code']=df.index.map(s)
        self.df = df
        return df.iterrows()

    def get_items(self, n):
        return n[0], n[1]['cwe'], n[1]['real vulnerability'], n[1]['code']

