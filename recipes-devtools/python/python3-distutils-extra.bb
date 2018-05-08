require python-distutils-extra.inc

inherit setuptools3 python3native native

S = "${WORKDIR}/python-distutils-extra-${PV}"

DISTUTILS_INSTALL_ARGS += "--install-lib=${libdir}/${PYTHON_DIR}/site-packages"

RDEPENDS_${PN} = "\
    python3-setuptools \
"
