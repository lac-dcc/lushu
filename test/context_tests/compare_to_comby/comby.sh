#!/bin/sh
#
# Usage: ./comby.sh "" "number_of_tokens"

num_tokens=${1:-10}
output_dir=${2:-res_$num_tokens} 
timeout_sec=${3:-20} 

comby '{ :[body] }' ':[body]' example/C/C_files/${num_tokens}.c -stdout -timeout $timeout_sec | \
    comby -count -stdin -match-only '{ :[body] }' '' -rule 'where nested' example/C/C_files/${num_tokens}.c -timeout $timeout_sec > "$output_dir"

