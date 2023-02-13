import re
import string
import numpy as np

# from argparse import ArgumentParser

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
    while not all(map(lambda x: x[1] - x[0] >= len(temp), zip(ids, [n] + ids))):
        ids = sorted(list(np.random.choice(rng, noc)), reverse=True)
    
    s = ''
    for x, y in zip(ids, [n] + ids):
        sublen = y - x
        sub = temp + generate_without(sublen - len(temp), temp)
        s = sub + s
    if len(s) < n:
        s = generate_without(n - len(s), temp) + s
    return s


if __name__ == '__main__':
    print(generate_without(100, 'Ass'))
    print(generate_with(20, 'pidorasina', 1, -1))
    print(temp := generate_template(20))
    print(s := generate_string(1000, temp, noc=5))
    print(temp in s)
    print(len([m.start() for m in re.finditer(temp, s)]))
