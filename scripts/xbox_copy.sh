#!/bin/sh

set -e

echo "XBOX ftp transfer script"

if [ "$#" -ne 3 ]; then
  echo "Wrong number of parameters"
  echo "Usage: $0 a b c"
  echo " a = ip address"
  echo " b = local filename"
  echo " c = xbox destination"
fi

ftp -n $1 <<END_SCRIPT
quote USER xbox
quote PASS xbox
put $2 $3
quit
END_SCRIPT

