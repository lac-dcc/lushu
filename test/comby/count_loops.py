import subprocess

def main():
    with open('count-for.txt') as pattern_file:
        comby_pattern = pattern_file.read().strip()
    comby_command = f"comby -count -match-only '{comby_pattern}' '' main.c"
    comby_output = subprocess.check_output(comby_command, shell=True).decode("utf-8")
    print(comby_output, end="")

if __name__ == "__main__":
    main()
