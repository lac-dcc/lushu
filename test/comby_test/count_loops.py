import subprocess

# Read the contents of count_for_loops.comby
with open('count_for_loops.comby', 'r') as pattern_file:
    comby_pattern = pattern_file.read().strip()

# Comby command to search for loops based on the pattern in count_for_loops.comby
comby_command = f"comby -matcher .c '{comby_pattern}' loop_program.c"

# Execute the Comby command and get the output
comby_output = subprocess.check_output(comby_command, shell=True).decode("utf-8")

# Split the output into lines
lines = comby_output.split("\n")

# Counter for loops based on the pattern
matched_loop_count = 0

# Loop through the lines of Comby output
for line in lines:
    # Check if the line is a match
    if "->" in line:
        # Increment the counter
        matched_loop_count += 1

# Display the result
print("Number of loops matching the pattern:", matched_loop_count)
