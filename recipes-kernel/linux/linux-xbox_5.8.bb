KBRANCH ?= "xbox-linux"

LINUX_KERNEL_TYPE = "tiny"
KCONFIG_MODE = "--allnoconfig"

require recipes-kernel/linux/linux-yocto.inc

LINUX_VERSION ?= "5.8.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

DEPENDS += "${@bb.utils.contains('ARCH', 'x86', 'elfutils-native', '', d)}"
DEPENDS += "openssl-native util-linux-native"

KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "2"
KCONF_AUDIT_LEVEL = "2"

SRCREV_machine ?= "cc89bd62acde4130b24854711db18c6513678484"
SRCREV_meta ?= "AUTOINC"

PV = "${LINUX_VERSION}+git${SRCPV}"

SRC_URI = "git://github.com/xboxdev/xbox-linux.git;protocol=https;branch=xbox-linux;name=machine \
 git://github.com/uglyoldbob/kernel_bsp.git;protocol=https;branch=main;type=kmeta;name=meta;destsuffix=${KMETA}"
#
#           git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=yocto-5.8;destsuffix=${KMETA}"

COMPATIBLE_MACHINE = "xbox"

# Functionality flags
KERNEL_FEATURES = ""


