#!/bin/bash

# Run this script from the root directory of Lushu
min_logs="$1"
step="$2"
max_logs="$3"
merger_config="$4"
results_dir="$5"

num_simuls_each=10

mkdir -p "$results_dir"
gradle StressTestGrammarStatisticsJar

num_logs="$min_logs"
temp="$(mktemp)"
echo a > "$temp"
while [ "$num_logs" -le "$max_logs" ]; do
    simul_num=0
    while [ "$simul_num" -lt "$num_simuls_each" ]; do
        echo "Running with ${merger_config}, simul $simul_num, $num_logs logs"
        java -jar ./Lushu/build/libs/StressTestGrammarStatistics.jar \
             "$merger_config" \
             "$temp" \
	           Lushu/src/test/fixtures/logs/log-generator \
             "$num_logs" \
             1>/dev/null \
             2>"$results_dir/$num_logs-$simul_num"
        simul_num=$(( simul_num + 1 ))
    done
    num_logs=$(( num_logs + step ))
done
