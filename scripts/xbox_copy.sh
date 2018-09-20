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

FPATH=${3%/*}
echo "Path is ${FPATH}"
FILE=${3##*/}
echo "Filename is ${FILE}"

ftp -n $1 << END_SCRIPT
quote USER xbox
quote PASS xbox
cd "$FPATH"
put "$2" "$FILE"
quit
END_SCRIPT

