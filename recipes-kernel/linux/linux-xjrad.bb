require recipes-kernel/linux/linux-yocto_6.6.bb
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

COMPATIBLE_MACHINE = "xjrad"

SRC_URI:append = " \
    file://xjrad;type=kmeta;destsuffix=xjrad \
    file://0001-Add-nvgpu-driver-and-related-code.patch \
"
