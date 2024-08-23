DESCRIPTION = "A custom image for an automotive head unit."
LICENSE = "MIT"

#IMAGE_FEATURES += "splash package-management ssh-server-openssh"
#IMAGE_FEATURES += "x11-base"
# Uncomment below to include dev tools and packages
# IMAGE_FEATURES += "tools-sdk dev-pkgs"

IMAGE_LINGUAS = "en-us"

IMAGE_FSTYPES = "wic wic.gz wic.zst"

inherit core-image swupdate-enc 

WKS_FILE = "auto-radio.wks"

FILESEXTRAPATHS:prepend := "${TOPDIR}:"

SRC_URI += "file://conf/keys.conf \
"

IMAGE_INSTALL += " \
	kernel-base \
	swupdate \
	swupdate-www \
"

# Create a user account with a password
# this command generates a hash for the useradd command
# the password is prompted interactively
# mkpasswd -m sha256crypt
inherit extrausers
EXTRA_USERS_PARAMS = " \
    useradd -p '$5$SbHarFQFMohIwIjP$4KaSsQDCraqEGzJewZk.2BYBzpVhxdx4EX/omngxF35' -G sudo xbox; \
"


