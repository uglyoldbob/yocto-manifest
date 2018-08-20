#!/bin/sh

if [ "$#" -ne 2 ]; then
    echo "Illegal number of parameters"
    echo "Usage - $0 a b"
    echo " a = deployment directory used by bitbake"
    echo " b = device to copy to"
    exit 1
fi

for n in $2* ; do umount $n ; done

echo "Deploy dir = $1"
files="$1/*.wic"
for f in $files; do
  if [ -z "$newest" ]; then
    newest=$f
  fi
  if [[ $f -nt $newest ]]; then
    newest=$f
  fi
done
echo "Temp name2 = $newest"
#bmaptool create $newest > ./image.bmap
echo $(sudo bmaptool copy --bmap $1/image.bmap $newest $2)
#rm ./image.bmap

sfdisk -c $2 1 a2
