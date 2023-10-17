#!/bin/sh
#
# Usage: ./count_loops.sh "my-file.c"

file=$1
[ -z "$file" ] && echo "Please provide a file to process" && exit 1
comby 'for (:[args]) { :[body] }' ':[body]' main.c -stdout | \
  comby -count -stdin -match-only 'for (:[args]) {:[body]}' '' -rule 'where nested' -matcher .c
