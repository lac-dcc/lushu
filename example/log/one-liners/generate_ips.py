from sys import argv
from random import randint

def main():
    if len(argv) < 2:
        return
    num_ips = int(argv[1])
    for i in range(num_ips):
        print(f"{randint(0, 255)}.{randint(0, 255)}.{randint(0, 255)}.{randint(0, 255)}",
              end="")
        if i != num_ips - 1:
            print("", end=" ")

if __name__ == '__main__':
    main()
