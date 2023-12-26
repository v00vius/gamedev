#!/bin/sh
#
# 1. to generate system headers like iostream 
headers="`grep -h -E 'import[ \t]+<.*>;' *.cpp */*.cpp |awk '{print \$NF}' |sed 's/[;<>]//g'|sort -u`"
echo "$headers"

g++ -std=c++20 -fmodules-ts -c -x c++-system-header $headers


