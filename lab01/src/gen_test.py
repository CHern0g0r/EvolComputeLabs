import string
import numpy as np
import pandas as pd

from tqdm import tqdm
from pathlib import Path
from argparse import ArgumentParser

A = list(string.ascii_letters)


def generate_by_len(n: int) -> str:
    raw = np.random.choice(A, n)
    return ''.join(raw)


def generate_without(n: int, sub: str) -> str:
    gen = generate_by_len(n)
    while sub in gen:
        gen = generate_by_len(n)
    return gen


def generate_with(n: int, sub: str, fr: int = None, to: int = None) -> str:
    part = sub[fr: to]
    remains = n - len(part)
    prelen = np.random.randint(remains)
    poslen = remains - prelen
    pre = generate_without(prelen, part)
    pos = generate_without(poslen, part)
    return pre + part + pos


def generate_template(n: int, rep: int = 1) -> str:
    return generate_by_len(n) * rep


def generate_string(n: int, temp: str, noc: int = 1):
    rng = np.arange(n)
    ids = sorted(list(np.random.choice(rng, noc)), reverse=True)
    i = 0
    while not all(map(
                  lambda x: x[1] - x[0] >= len(temp),
                  zip(ids, [n] + ids))):
        i += 1
        ids = sorted(list(np.random.choice(rng, noc)), reverse=True)
        if i == 20:
            i = 0
            noc -= 1

    s = ''
    for x, y in zip(ids, [n] + ids):
        sublen = y - x
        sub = temp + generate_without(sublen - len(temp), temp)
        s = sub + s
    if len(s) < n:
        s = generate_without(n - len(s), temp) + s

    return s, ids


def generate_test(temp_len, str_len, noc):
    t = generate_template(temp_len)
    s, ids = generate_string(str_len, t, noc)
    return t, s, [i for i in ids]


def main(args):
    n = args.n
    tlens = np.random.randint(1, 100, n)
    slens = np.random.binomial(1000, 0.5, size=n) + tlens
    max_noc = slens // tlens
    nocs = np.random.randint(1, max_noc)
    mask = np.random.binomial(1, 0.95, size=n)
    nocs *= mask

    data = {
        'string': [],
        'template': [],
        'ids': []
    }
    for tl, sl, noc in tqdm(zip(tlens, slens, nocs)):
        t, s, i = generate_test(tl, sl, noc)
        data['string'].append(s)
        data['template'].append(t)
        data['ids'].append(i)
    data = pd.DataFrame(data)
    data.to_csv(args.path, index=False)


if __name__ == '__main__':
    parser = ArgumentParser()
    parser.add_argument('path')
    parser.add_argument('--n', default=1000, type=int)
    args = parser.parse_args()

    args.path = Path(args.path)
    args.path.parents[0].mkdir(parents=True, exist_ok=True)
    main(args)
