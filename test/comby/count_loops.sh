#!/bin/sh
#
# Usage: ./count_loops.sh "my-file.c"

file=$1
[ -z "$file" ] && echo "Please provide a file to process" && exit 1
comby -count -match-only "$(cat count-for.txt)" '' "$file"
