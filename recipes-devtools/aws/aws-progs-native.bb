DESCRIPTION = "AWS based programs"
SECTION = "base"
LICENSE = "GPLv3"

DEPENDS = "aws-native-dev"

LIC_FILES_CHKSUM = "file://LICENSE;md5=d32239bcb673463ab874e80d47fae504"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SRC_URI = " \
	git://github.com/uglyoldbob/aws_iot_programs.git;rev=master;branch=master \
	"

S = "${WORKDIR}/git"

#BUILD_CFLAGS += " -g -O0"

inherit native autotools
