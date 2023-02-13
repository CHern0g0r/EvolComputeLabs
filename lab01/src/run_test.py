import pandas as pd
import numpy as np

from algo import boyer_moore


if __name__ == '__main__':
    df = pd.read_csv('../test/test.csv')

    res = []
    for i, row in df.iterrows():
        s, t, ids = row
        ids = eval(ids)
        pred = boyer_moore(s, t)
        res += [set(ids) == set(pred)]
    res = np.array(res)
    print('Trues: ', np.sum(res), '/', len(df), sep='')
