import re
import resource
import pandas as pd
import numpy as np

from time import time
from argparse import ArgumentParser

from algo import boyer_moore, parallel
from algoc import boyer_moorec


_CASES = {
    "base": boyer_moore,
    "prll": parallel,
    "fast": boyer_moorec
}


def test(test_path: str, case: str = 'base'):
    df = pd.read_csv(test_path)

    bm = _CASES.get(case, boyer_moore)
    acc_t = 0
    res = []
    for i, row in df.iterrows():
        s, t, ids = row
        ids = eval(ids)

        t_start = time()
        pred = bm(s, t)
        t_end = time()
        acc_t += t_end - t_start

        ids = set([m.start() for m in re.finditer(f'(?={t})', s)])
        pred = set(pred)

        ans = ids == pred

        res += [ans]

    print(resource.getrusage(resource.RUSAGE_SELF).ru_maxrss)

    res = np.array(res)
    mean_time = acc_t / len(df)

    return np.sum(res), len(df), mean_time


def compare(test_path: str):
    res = [(k,) + test(test_path, k) for k, v in _CASES.items()]
    print('\n'.join(
        map(
            lambda x: f'{x[0]}: {x[1] / x[2] * 100}% acc; mean_time = {x[3]}',
            sorted(res, key=lambda x: x[-1])
        )
    ))


if __name__ == '__main__':
    parser = ArgumentParser()
    parser.add_argument('--path', default='../test/test.csv')
    parser.add_argument('--case', default='base')
    parser.add_argument('--compare', action='store_true')
    args = parser.parse_args()
    
    if args.compare:
        compare(args.path)
    else:
        r, l, t = test(args.path, args.case)
        print('Trues: ', r, '/', l, sep='')
        print('Mean time:', t, 'sec')
