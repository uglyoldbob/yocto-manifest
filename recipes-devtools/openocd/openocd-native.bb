SUMMARY = "Free and Open On-Chip Debugging, In-System Programming and Boundary-Scan Testing"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"
DEPENDS = "libusb-compat libftdi"
RDEPENDS_${PN} = "libusb1"

SRC_URI = "git://repo.or.cz/openocd.git \
           file://0001-Fix-libusb-1.0.22-deprecated-libusb_set_debug-with-l.patch \
           file://cyclone.cfg \
           file://gdbstart \
          "
SRCREV = "cdf1e826eb23c29de1019ce64125f644f01b0afe"

PV = "0.10+gitr${SRCPV}"
S = "${WORKDIR}/git"

inherit pkgconfig autotools-brokensep gettext native

BBCLASSEXTEND += "nativesdk"

EXTRA_OECONF = "--enable-ftdi --disable-doxygen-html "

do_configure() {
    ./bootstrap
    oe_runconf ${EXTRA_OECONF}
    cp ${WORKDIR}/cyclone.cfg ${WORKDIR}/git/tcl/target
}

addtask openocd after do_populate_sysroot
python do_openocd () {
  pathdir = d.getVar("datadir", True)
  print os.getcwd()
  newpid = os.fork()
  if newpid == 0:
    time.sleep(5)
    comand="gdb --command=../gdbstart"
    oe_terminal("${SHELL} -c \"%s; if [ \$? -ne 0 ]; then echo 'Command failed.'; printf 'Press any key to continue... '; read r; fi\"" % comand, 'Running gdb', d)
    exit(0)
  else:
    comand="openocd -f ./tcl/interface/altera-usb-blaster2.cfg -f ./tcl/target/cyclone.cfg"
    oe_terminal("${SHELL} -c \"%s; if [ \$? -ne 0 ]; then echo 'Command failed.'; printf 'Press any key to continue... '; read r; fi\"" % comand, 'Running openocd', d)
  bb.fatal("This is so the command will always run")
}

do_install() {
    oe_runmake DESTDIR=${D} install
}

PACKAGECONFIG[sysfsgpio] = "--enable-sysfsgpio,--disable-sysfsgpio"
PACKAGECONFIG ??= "sysfsgpio"
