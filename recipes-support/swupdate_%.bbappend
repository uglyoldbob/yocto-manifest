FILESEXTRAPATHS:prepend := "${TOPDIR}:${THISDIR}/swupdate:"

addtask add_encryption_data after do_compile before do_install

SRC_URI += "file://tempdefconfig \
	file://tempswupdate.service \
	file://conf/signing/public.pem \
	file://conf/keys.conf \
	file://swupdate.cfg \
"

DEPENDS += " libarchive curl"

inherit swupdate-lib

python do_add_encryption_data() {
    workdir = d.getVar('WORKDIR', True)
    s = d.getVar('S', True)
    with open(os.path.join(s, "encryption_key"), 'w+') as g:
        k, ivt = swupdate_extract_keys(os.path.join(workdir, "conf/keys.conf"))
        g.write(k)
        g.write(" ")
        g.write(ivt)
}

do_configure () {
    rm -f ${WORKDIR}/defconfig
    cp ${WORKDIR}/tempdefconfig ${WORKDIR}/defconfig
    cat ${WORKDIR}/tempdefconfig >> ${WORKDIR}/.config
    cp ${WORKDIR}/.config ${S}/.config
    (cd ${S} && cml1_do_configure)
}

do_install:append() {
	install -d ${D}/etc/
	install -d ${D}/etc/swupdate
	install -m 0600 ${WORKDIR}/swupdate.cfg ${D}/etc/swupdate.cfg
	install -m 0600 ${WORKDIR}/conf/signing/public.pem ${D}/etc/swupdatepub.key
	install -m 0600 ${S}/encryption_key ${D}/etc/swupdate/encryption
	install -m 0600 ${WORKDIR}/tempswupdate.service ${D}/usr/lib/systemd/system/swupdate.service
}

