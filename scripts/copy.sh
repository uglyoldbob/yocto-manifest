#!/bin/sh

if [ "$#" -ne 2 ]; then
    echo "Illegal number of parameters"
    echo "Usage - $0 a b"
    echo " a = image name"
    echo " b = device to copy to"
    exit 1
fi

mach=$(bitbake -e | grep "MACHINE_ARCH=" | cut -d'"' -f2)
machfolder="./tmp/deploy/images/$mach"

calcname="$machfolder/$1-$mach.wic"
calcname2="$machfolder/$1-$mach.bmap"

for n in $2* ; do umount $n ; done

echo "Deploy dir = $machfolder"

echo $(sudo bmaptool copy --bmap $calcname2 $calcname $2)

sfdisk -c $2 1 a2
