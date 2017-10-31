FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PV = "3.18.21"

SRC_URI_append += "file://0001-update-device-tree-for-touch-lcd43-on-chesnut.patch"

