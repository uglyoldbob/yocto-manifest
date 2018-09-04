DESCRIPTION = "AWS based programs"
SECTION = "base"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://aws/LICENSE.txt;md5=acc7a1bf87c055789657b148939e4b40"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SRC_URI = " \
	git://github.com/aws/aws-iot-device-sdk-embedded-C.git;rev=release;branch=release;destsuffix=git/aws \
	git://github.com/cpputest/cpputest.git;rev=2139e96793f736102f9e4c465a20f8a72daab0a0;destsuffix=git/aws/external_libs/CppUTest \
	git://github.com/ARMmbed/mbedtls.git;rev=mbedtls-2.7;branch=mbedtls-2.7;destsuffix=git/aws/external_libs/mbedTLS \
	"

S = "${WORKDIR}/git"

inherit autotools native
