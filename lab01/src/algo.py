from argparse import ArgumentParser

def boyer_moore(s: str, t: str) -> int:
    return 0


if __name__ == '__main__':
    parser = ArgumentParser()
    parser.add_argument('string')
    parser.add_argument('template')
    args = parser.parse_args()

    boyer_moore(args.string, args.template)