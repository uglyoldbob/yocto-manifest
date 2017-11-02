do_install_append() {
	# Make journal storage volatile only
	sed -i -e 's/.*Storage.*/Storage=volatile/' ${D}${sysconfdir}/systemd/journald.conf
}

