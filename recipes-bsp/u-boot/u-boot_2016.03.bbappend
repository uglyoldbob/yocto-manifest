FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PV = "v2016.03+git${SRCPV}"

COMPATIBLE_MACHINE = "de0nano"

SRC_URI_append += "file://0001-Increase-maximum-number-of-sectors-that-the-spl-will.patch \
	file://0002-Change-which-partition-is-defined-as-the-root-partit.patch \
	file://0003-Don-t-enable-warm-boot-from-OCRAM.patch \
	file://handoff/* "

do_compile_prepend_de0nano() {
  if [ -d ./handoff ]; then
    if [ -d ./bspin ]; then
      arch/arm/mach-socfpga/qts-filter.sh cyclone5 ./handoff ./bspin board/altera/cyclone5-socsdk/qts
    fi
  fi

  echo $(pwd)
  echo "BLABLABLABLABLABLABLLABLABLABLABLABLABL"
}
