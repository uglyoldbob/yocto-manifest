require recipes-kernel/linux/linux-yocto.inc

COMPATIBLE_MACHINE = "xbox"

LINUX_VERSION = "2.6.18"
LINUX_VERSION_EXTENSION = "-custom"

BOOT_SPLASH ?= "logo_linux_clut224-generic.ppm"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-xbox:"

S = "${WORKDIR}/git"

SRCREV = "299a2479bca6211f845158761920ec480f35a229"
SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;nocheckout=1;branch=linux-2.6.18.y \
    file://defconfig \
	file://first.patch \
    file://${BOOT_SPLASH} \
"
