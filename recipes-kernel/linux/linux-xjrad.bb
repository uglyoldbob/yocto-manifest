require recipes-kernel/linux/linux-yocto_6.6.bb
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

COMPATIBLE_MACHINE = "xjrad"

SRC_URI:append = " \
    file://xjrad;type=kmeta;destsuffix=xjrad \
"
