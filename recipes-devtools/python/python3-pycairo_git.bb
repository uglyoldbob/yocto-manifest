SUMMARY = "Python bindings for the Cairo canvas library"
HOMEPAGE = "http://cairographics.org/pycairo"
BUGTRACKER = "http://bugs.freedesktop.org"
SECTION = "python-devel"
LICENSE = "LGPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504 \
                    file://COPYING.LESSER;md5=e6a600fd5e1d9cbde2d983680233ad02"

# cairo >= 1.8.8
DEPENDS = "python3 cairo"
PR = "r2"

SRCREV = "5a45b0bfeccd3a0f2a6467bafcad515428439190"
SRC_URI = "git://anongit.freedesktop.org/git/pycairo"


S = "${WORKDIR}/git"

inherit distutils3 pkgconfig

BBCLASSEXTEND = "native"

export BUILD_SYS
export HOST_SYS

EXTRA_OEMAKE = '\
  BUILD_SYS="" \
  HOST_SYS="" \
  LIBC="" \
  STAGING_LIBDIR=${STAGING_LIBDIR_NATIVE} \
  STAGING_INCDIR=${STAGING_INCDIR_NATIVE} \
  LIB=${baselib} \
  ARCH=${TARGET_ARCH} \
'

LIBDIR = "${D}"
export LIBDIR

do_configure() {
	PYTHON=${PYTHON} ./waf configure --prefix=${D}${prefix} --libdir=${D}${libdir}
}

do_compile() {
	./waf build ${PARALLEL_MAKE}
}

do_install() {
	./waf install
	sed \
		-e 's:@prefix@:${prefix}:' \
		-e 's:@VERSION@:${PV}:' \
		-e 's:@includedir@:${includedir}:' \
		py3cairo.pc.in > py3cairo.pc
	install -m 0644 py3cairo.pc ${D}${libdir}/pkgconfig/
}
