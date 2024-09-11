FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = "file://fragment.cfg \
	file://0001-Tweak-device-trees-for-custom-hardware-and-for-compi.patch \
"

