require recipes-kernel/linux/linux-yocto_6.6.bb
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

COMPATIBLE_MACHINE = "xjrad"

SRC_URI:append = " \
    file://xjrad;type=kmeta;destsuffix=xjrad \
    file://0001-Add-the-device-tree-to-the-kernel.patch \
    file://0002-Support-for-nvgpu-module.patch \
    file://0003-Add-vpr-code-for-nvgpu-support.patch \
    file://0004-Update-fuse-code-for-nvgpu-support.patch \
    file://0005-Add-hypervisor-check-code-for-nvgpu-support.patch \
"
