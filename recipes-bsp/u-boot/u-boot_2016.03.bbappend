FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PV = "v2016.03+git"

COMPATIBLE_MACHINE = "de0nano"

SRC_URI_append += "file://0001-Increase-maximum-number-of-sectors-that-the-spl-will.patch \
	file://0002-Change-which-partition-is-defined-as-the-root-partit.patch \
	file://0003-Don-t-enable-warm-boot-from-OCRAM.patch \
	file://0004-Add-fpga-loading-to-u-boot.patch"

do_compile_prepend() {
	if [ -d ${WORKDIR}/handoff ]; then
		[ -d ${WORKDIR}/bsp ]; then
			${WORKDIR}/git/arch/arm/mach-socfpga/qts-filter.sh cyclone5 "${WORKDIR}/handoff" "${WORKDIR}/bsp" "${WORKDIR}/git/board/altera/cyclone5-socdk/qts"
		fi
	fi
}

