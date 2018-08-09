#!/bin/sh

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
bmaptool create $newest > ./image.bmap
echo $(sudo bmaptool copy --bmap ./image.bmap $newest $2)
rm ./image.bmap
