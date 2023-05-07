#!/bin/bash

# Run this script from the root directory of Lushu
min_logs="$1"
step="$2"
max_logs="$3"
jar_prefix="$4"
output_dir="$5"

# Prepare
mkdir -p "$output_dir"
gradle "${jar_prefix}Jar"

# For each number of logs, we run it this many times to minimize the effect of
# noise.
num_simuls_each=10

num_logs="$min_logs"
while [ "$num_logs" -le "$max_logs" ]; do
    simul_num=0
    while [ "$simul_num" -lt "$num_simuls_each" ]; do
        echo "$simul_num running with $num_logs logs"
        java -jar "./Lushu/build/libs/${jar_prefix}.jar" \
	           example/config.yaml \
	           example/log/train/cpf-is-sensitive.log \
	           Lushu/src/test/fixtures/logs/log-generator \
             "$num_logs" \
             1> /dev/null \
             2> "$output_dir/${num_logs}-${simul_num}"
        simul_num=$(( simul_num + 1 ))
    done
    num_logs=$(( num_logs * step ))
done
