from argparse import ArgumentParser


def z_func(s):
    z = [0] * len(s)
    left, right = 0, 0
    for i in range(1, len(s)):
        z[i] = max(0, min(z[i - left], right - i))
        while i + z[i] < len(s) and s[z[i]] == s[i + z[i]]:
            z[i] += 1
        if i + z[i] > right:
            left, right = i, i + z[i]
    return z


def boyer_moore(s: str, t: str) -> int:
    res = []

    st = dict()
    for i, c in enumerate(t[:-1]):
        st[c] = i

    ln = len(t)

    z = [0] * ln
    m, idx = 0, 0
    for i in range(1, ln):
        if i <= m:
            z[i] = min(z[i - idx], m - i + 1)
        while (i + z[i] < ln and
               t[ln - 1 - z[i]] == t[ln - 1 - (i + z[i])]):
            z[i] += 1
        if i + z[i] - 1 > m:
            m, idx = i, i + z[i] - 1

    ss = [ln] * (ln + 1)
    for i in range(ln - 1, 0, -1):
        ss[ln - z[i]] = i
    r = 0
    for i in range(1, ln):
        if i + z[i] == ln:
            while r <= i:
                if ss[r] == ln:
                    ss[r] = i
                r += 1

    bnd, i, j = (0,) * 3
    while i <= len(s) - ln:
        j = ln - 1
        while j >= bnd and t[j] == s[i + j]:
            j -= 1
        if j < bnd:
            res += [i]
            bnd = ln - ss[0]
            j = -1
        else:
            bnd = 0

        # i += ss[j + 1]
        if j < bnd:
            i += ss[j + 1]
        else:
            i += max(ss[j + 1], j - st.get(s[i + j], -1))

    return res


if __name__ == '__main__':
    parser = ArgumentParser()
    parser.add_argument('string')
    parser.add_argument('template')
    args = parser.parse_args()

    print(boyer_moore(args.string, args.template))
