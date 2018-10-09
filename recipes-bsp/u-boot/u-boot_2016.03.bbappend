FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PV = "v2016.03+git"

COMPATIBLE_MACHINE = "de0nano"

SRC_URI_append += "file://0001-Many-fixes-for-cyclone-V.patch"

do_compile_prepend() {
	if [ -d "${WORKDIR}/handoff" ]; then
		if [ -d "${WORKDIR}/bsp" ]; then
			${WORKDIR}/git/arch/arm/mach-socfpga/qts-filter.sh cyclone5 "${WORKDIR}/handoff" "${WORKDIR}/bsp" "${WORKDIR}/git/board/altera/cyclone5-socdk/qts"
		fi
	fi
}

