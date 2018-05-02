FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://lxdms.conf \
    file://Tiny/ \
"

do_compile_append() {
    cp ${WORKDIR}/lxdms.conf ${WORKDIR}/build/data/lxdm.conf
}

do_install_append() {
    echo "BLABLA FOLDER = ${D}${sysconfdir}/lxdm"
    echo "BLABLA FILE =  ${WORKDIR}/lxdm.conf"
    install -d ${D}/usr/share/lxdm/themes/Tiny
    install -m 644 -t ${D}/usr/share/lxdm/themes/Tiny ${WORKDIR}/Tiny/*
}
