DESCRIPTION = "MbedTLS library"
SECTION = "base"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://LICENSE;md5=302d50a6369f5f22efdb674db908167a"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SRC_URI = " \
	git://github.com/ARMmbed/mbedtls.git;rev=mbedtls-2.7;branch=mbedtls-2.7;destsuffix=git \
	file://0001-Build-shared-instead-of-static-library.patch \
	"

S = "${WORKDIR}/git"

#BUILD_CFLAGS += " -g -O0"

inherit native cmake
