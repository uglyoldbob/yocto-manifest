DESCRIPTION = "Nvgpu driver for jetson nano"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit module

SRC_URI = "file://0001-Initial-commit-with-modified-nvgpu-code.patch \
	file://0002-More-mods.patch \
"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

RPROVIDES:${PN} += "kernel-module-nvgpu"
