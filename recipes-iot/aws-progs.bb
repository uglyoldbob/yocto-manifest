DESCRIPTION = "AWS based programs"
SECTION = "base"
LICENSE = "GPLv3"

DEPENDS = "aws"
RDEPENDS_aws-progs-keygen = "systemd openssl"

LIC_FILES_CHKSUM = "file://LICENSE;md5=d32239bcb673463ab874e80d47fae504"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SRC_URI = " \
	git://github.com/uglyoldbob/aws_iot_programs.git;rev=master;branch=master \
	"

do_install_append() {
  install -d ${D}/etc/systemd/system/multi-user.target.wants
  ln -s -r ${D}/etc/systemd/system/iotgenkeys.service ${D}/etc/systemd/system/multi-user.target.wants/iotgenkeys.service
}

S = "${WORKDIR}/git"

#BUILD_CFLAGS += " -g -O0"

EXTRA_OECONF = "--prefix=/ --exec-prefix=/ --bindir=/bin --libdir=/lib"

PROVIDES += "${PN}-keygen"
PACKAGES += "${PN}-keygen"

FILES_${PN}-keygen =+ "${sysconfdir}/iot/openssl.cnf \
 ${sysconfdir}/systemd/system/iotgenkeys.service \
 ${sysconfdir}/systemd/system/multi-user.target.wants/iotgenkeys.service \
"

FILES_${PN} = "${base_bindir}/aws-prog1"

inherit autotools
