FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:xjrad = "file://xjrad;type=kmeta;destsuffix=xjrad \
	file://0001-Add-device-tree-for-custom-hardware-based-on-jetson-.patch \
"
SRC_URI:append:xjrad-old = "file://xjrad_old;type=kmeta;destsuffix=xjrad_old \
"
