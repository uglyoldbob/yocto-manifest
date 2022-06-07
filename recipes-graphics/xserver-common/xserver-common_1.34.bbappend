FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

#RDEPENDS_${PN}_remove = "xinput-calibrator"

do_compile:prepend() {
  echo "Remove from ${WORKDIR}/${PN}-${PV}/X11/Xinit.d/"
  rm -rf ${WORKDIR}/${PN}-${PV}/X11/Xinit.d/89xTs_Calibrate
  rm -rf ${WORKDIR}/${PN}-${PV}/X11/Xinit.d/55xScreenSaver
}

