DESCRIPTION = "AWS library"
SECTION = "base"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=acc7a1bf87c055789657b148939e4b40"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SRC_URI = " \
	git://github.com/aws/aws-iot-device-sdk-embedded-C.git;rev=release;branch=release;destsuffix=git/aws \
	git://github.com/ARMmbed/mbedtls.git;rev=mbedtls-2.7;branch=mbedtls-2.7;destsuffix=git/aws/external_libs/mbedTLS \
	"

do_compile_prepend() {
  cp ${S}/samples/linux/subscribe_publish_library_sample/Makefile ${S}
  sed -i -e 's/..\/..\/../.\//' ${S}/Makefile
  cp ${S}/samples/linux/subscribe_publish_library_sample/aws_iot_config.h ${S}
  cp ${S}/samples/linux/subscribe_publish_library_sample/subscribe_publish_library_sample.c ${S}
}

S = "${WORKDIR}/git/aws"

do_install_append() {
	install -d ${D}${libdir}
	install -d ${D}${includedir}
	install -d ${D}${includedir}/aws
	install -m 0644 libAwsIotSdk.a ${D}${libdir}
	install -m 0755 ${S}/include/* ${D}${includedir}/aws
}

PROVIDES += "${PN}-dev ${PN}-staticdev"
PACKAGES += "${PN}-dev ${PN}-staticdev"

FILES_${PN}-staticdev = "${libdir}/*.a"
FILES_${PN}-dev = "${S}/include"

inherit native pkgconfig
