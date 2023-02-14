import re
import pandas as pd
import numpy as np

from time import time

from algo import boyer_moore, parallel


def test(test_path: str = '../test/test.csv'):
    df = pd.read_csv(test_path)

    acc_t = 0
    res = []
    bad_ans = []
    for i, row in df.iterrows():
        s, t, ids = row
        ids = eval(ids)

        t_start = time()
        # pred = boyer_moore(s, t)
        pred = parallel(s, t, n=100)
        t_end = time()
        acc_t += t_end - t_start

        ids = set([m.start() for m in re.finditer(f'(?={t})', s)])
        pred = set(pred)

        ans = ids == pred
        if not ans:
            print(i)
            print(ids - pred)
            print(pred - ids)

        res += [ans]

    res = np.array(res)
    mean_time = acc_t / len(df)
    print('Trues: ', np.sum(res), '/', len(df), sep='')
    print('Mean time:', mean_time, 'sec')
    print(type(res))



if __name__ == '__main__':
    test()
