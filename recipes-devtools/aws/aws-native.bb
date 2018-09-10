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
  echo "clean:" >> ${S}/external_libs/CppUTest/Makefile
  cp ${S}/samples/linux/subscribe_publish_library_sample/Makefile ${S}
  sed -i -e 's/..\/..\/../.\//' ${S}/Makefile
  sed -i -e 's/LOG_FLAGS +=/#LOG_FLAGS +=/' ${S}/Makefile
  cp ${S}/samples/linux/subscribe_publish_library_sample/aws_iot_config.h ${S}
  cp ${S}/samples/linux/subscribe_publish_library_sample/subscribe_publish_library_sample.c ${S}
}

S = "${WORKDIR}/git/aws"

#BUILD_CFLAGS += " -g -O0"

do_install_append() {
	install -d ${D}${libdir}
	install -d ${D}${includedir}
	install -d ${D}${includedir}/aws
	install -d ${D}${includedir}/mbedtls
	install -m 0644 libAwsIotSdk.a ${D}${libdir}
	install -m 0644 ${S}/external_libs/mbedTLS/library/*.a ${D}${libdir}
	install -m 0755 ${S}/include/* ${D}${includedir}/aws
	install -m 0755 ${S}/external_libs/mbedTLS/include/mbedtls/* ${D}${includedir}/mbedtls
	install -m 0755 ${S}/platform/linux/common/timer_platform.h ${D}${includedir}/aws
	install -m 0755 ${S}/platform/linux/mbedtls/network_platform.h ${D}${includedir}/aws

	for filename in ${D}${includedir}/aws/*.h; do
	  sed -i -e 's/#include "aws_iot_config.h"//' $filename
	  sed -i -e 's/#include <aws_/#include <aws\/aws_/' $filename
	  sed -i -e 's/#include "aws_/#include "aws\/aws_/' $filename
#	  sed -i -e 's/#ifdef ENABLE_IOT_.\+/#if 1/' $filename
	done
}

PROVIDES += "${PN}-dev ${PN}-staticdev"
PACKAGES += "${PN}-dev ${PN}-staticdev"

FILES_${PN}-staticdev = "${libdir}/*.a"
FILES_${PN}-dev = "${S}/include"

inherit native pkgconfig
