DESCRIPTION = "MbedTLS library"
SECTION = "base"
LICENSE = "Apache-2.0"

inherit cmake

DEPENDS = "perl-native perl"

LIC_FILES_CHKSUM = "file://LICENSE;md5=302d50a6369f5f22efdb674db908167a"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SRC_URI = " \
	git://github.com/ARMmbed/mbedtls.git;rev=mbedtls-2.7;branch=mbedtls-2.7;destsuffix=git \
	file://0001-Build-shared-instead-of-static-library.patch \
	"

PACKAGECONFIG_CONFARGS="-DPERL_EXECUTABLE=`which perl`"

S = "${WORKDIR}/git"

#BUILD_CFLAGS += " -g -O0"

do_install_append() {
	rm ${D}/usr/lib/libmbedcrypto.so
	rm ${D}/usr/lib/libmbedtls.so
	rm ${D}/usr/lib/libmbedx509.so
	cp ${D}/usr/lib/libmbedcrypto.so.2.7.6 ${D}/usr/lib/libmbedcrypto.so
	cp ${D}/usr/lib/libmbedtls.so.2.7.6 ${D}/usr/lib/libmbedtls.so
	cp ${D}/usr/lib/libmbedx509.so.2.7.6 ${D}/usr/lib/libmbedx509.so
}

PROVIDES += "${PN}-examples ${PN}-dev ${PN}-staticdev"
PACKAGES =+ "${PN}-examples"

FILES_${PN} = "${libdir}/*"
FILES_${PN}-examples = "${bindir}/*"
FILES_${PN}-dev = "${includedir}/mbedtls/*"
