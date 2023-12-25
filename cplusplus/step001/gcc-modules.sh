#!/bin/sh
#
# 1. to generate system headers, iostream
headers="`grep -h -E 'import[ \t]+<.*>;' *.cpp */*.cpp */impl/*.cpp |awk '{print \$NF}' |sed 's/[;<>]//g'|sort -u`"
echo "$headers"

g++ -std=c++20 -fmodules-ts -c -x c++-system-header $headers

# 2. to compile
#g++ -std=c++20 -fmodules-ts hello.cpp main.cpp -o hello

