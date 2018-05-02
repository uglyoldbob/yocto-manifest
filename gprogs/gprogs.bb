DESCRIPTION = "Programs for use on a gumstix"
SECTION = "base"
DEPENDS = "gtk+ gtk+3"
RDEPENDS_${PN} = "gtk+3"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://LICENSE;md5=96af5705d6f64a88e035781ef00e98a8"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SRCREV = "master"
SRC_URI = "git://github.com/uglyoldbob/gumstix_progs.git"

REQUIRED_DISTRO_FEATURES += "x11"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

