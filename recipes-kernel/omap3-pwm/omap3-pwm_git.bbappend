FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PV = "1"

SRC_URI_append_overo += "file://0001-Fix-compilation.patch"
