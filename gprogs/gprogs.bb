DESCRIPTION = "Programs for use on a gumstix"
SECTION = "base"
DEPENDS = "gtk+ gtk+3"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://LICENSE;md5=96af5705d6f64a88e035781ef00e98a8"

inherit autotools pkgconfig update-alternatives

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SRCREV = "master"
SRC_URI = "git://github.com/uglyoldbob/gumstix_progs.git"

REQUIRED_DISTRO_FEATURES += "x11"

S = "${WORKDIR}/git"

ALTERNATIVE_${PN}-startup = "x-session-manager"
ALTERNATIVE_TARGET[x-session-manager] = "${bindir}/run-gprogs.sh"
ALTERNATIVE_PRIORITY = "60"

PROVIDES += "${PN}-startup"
PACKAGES += "${PN}-startup"

ALLOW_EMPTY_${PN}-startup = "1"
FILES_${PN}-startup = "${bindir}/run-gprogs.sh "

FILES_${PN} = "${bindir}/gprogs \
 ${sysconfdir}/gprog/main.ui"

