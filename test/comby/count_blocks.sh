#!/bin/sh
#
# Usage: ./count_loops.sh "my-file.c"

file=${1:-blocks.c}
comby '{ :[body] }' ':[body]' "$file" -stdout | \
    comby -count -stdin -match-only '{ :[body] }' '' -rule 'where nested' "$file"
