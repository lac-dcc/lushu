#!/bin/bash

# Run this script from the root directory of Lushu
min_logs="$1"
step="$2"
max_logs="$3"
# For each number of logs, we run it this many times to minimize the effect of
# noise.
num_simuls_each="$4"
output_dir="$5"
logfile="$6"

# Prepare
jar_prefix='stressTestMapWithLushu'
mkdir -p "$output_dir"
gradle "${jar_prefix}Jar"

tmpfile="$(mktemp)"

num_tokens="$min_logs"
while [ "$num_tokens" -le "$max_logs" ]; do
    head -$num_tokens "$logfile" > "$tmpfile"
    simul_num=0
    while [ "$simul_num" -lt "$num_simuls_each" ]; do
        echo "$simul_num running with $num_tokens logs"
        java -jar "./Lushu/build/libs/${jar_prefix}.jar" \
	           example/config.yaml \
               example/C/C_files/${num_tokens}.c \
               example/C/train/patterns.txt \
               test/context_tests/compare_to_comby/max_blocks/Lushu/${num_tokens}.txt \
             "$num_tokens" \
             "$tmpfile" \
             1> /dev/null \
             2> "$output_dir/${num_tokens}-${simul_num}"
        simul_num=$(( simul_num + 1 ))
    done
    num_tokens=$(( num_tokens * step ))
done
