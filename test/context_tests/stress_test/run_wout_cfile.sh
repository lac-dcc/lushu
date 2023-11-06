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
mkdir -p "$output_dir"

tmpfile="$(mktemp)"

num_tokens="$min_logs"
while [ "$num_tokens" -le "$max_logs" ]; do
    head -$num_tokens "$logfile" > "$tmpfile"
    simul_num=0
    while [ "$simul_num" -lt "$num_simuls_each" ]; do
        echo "$simul_num running with $num_tokens logs"
        /usr/bin/time -v  \
        bash test/context_tests/compare_to_comby/comby.sh $num_tokens "test/context_tests/compare_to_comby/max_blocks/Comby/${num_tokens}.txt" 1000 \
        grep maximum \
        1> /dev/null \
        2> "$output_dir/${num_tokens}-${simul_num}"
        simul_num=$(( simul_num + 1 ))
    done
    num_tokens=$(( num_tokens * step ))
done
