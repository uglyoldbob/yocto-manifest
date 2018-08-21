FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PV = "v2016.03+git${SRCPV}"

COMPATIBLE_MACHINE = "de0nano"

SRC_URI_append += "file://0001-Increase-maximum-number-of-sectors-that-the-spl-will.patch \
	file://0002-Change-which-partition-is-defined-as-the-root-partit.patch"

do_compile_prepend_de0nano() {
  echo $(pwd)
  echo "BLABLABLABLABLABLABLLABLABLABLABLABLABL"
}
