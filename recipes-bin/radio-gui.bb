DESCRIPTION = "Rust based radio frontend"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit cargo pkgconfig
#inherit cargo-update-recipe-crates

DEPENDS = "libdbus-c++"

#pull in generated crate info
include ${BPN}-crates.inc

S = "${WORKDIR}/git"

INSANE_SKIP:${PN}-dbg += "buildpaths"

#DEBUG_BUILD="1"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
	git://github.com/uglyoldbob/radio.git;name=radio;protocol=https;branch=master \
	git://github.com/emilk/egui.git;protocol=https;name=egui;branch=master;destsuffix=egui;skiprust=1 \
"

python egui_fix() {
    import shutil

    cargo_config = os.path.join(d.getVar("CARGO_HOME"), "config")
    if not os.path.exists(cargo_config):
        return

    src_uri = (d.getVar('SRC_URI') or "").split()
    if len(src_uri) == 0:
        return

    patches = dict()
    workdir = d.getVar('WORKDIR')
    with open(cargo_config, "a+") as config:
        print('\n[patch."https://github.com/emilk/egui.git"]', file=config)
        print('egui = { path = "%s"}' % (os.path.join(workdir, "egui/crates/egui")), file=config)
        print('eframe = { path = "%s"}' % (os.path.join(workdir, "egui/crates/eframe")), file=config)
        print('egui_extras = { path = "%s"}' % (os.path.join(workdir, "egui/crates/egui_extras")), file=config)

    # Cargo.lock file is needed for to be sure that artifacts
    # downloaded by the fetch steps are those expected by the
    # project and that the possible patches are correctly applied.
    # Moreover since we do not want any modification
    # of this file (for reproducibility purpose), we prevent it by
    # using --frozen flag (in CARGO_BUILD_FLAGS) and raise a clear error
    # here is better than letting cargo tell (in case the file is missing)
    # "Cargo.lock should be modified but --frozen was given"

    lockfile = d.getVar("CARGO_LOCK_PATH")
    if not os.path.exists(lockfile):
        bb.fatal(f"{lockfile} file doesn't exist")

    # There are patched files and so Cargo.lock should be modified but we use
    # --frozen so let's handle that modifications here.
    #
    # Note that a "better" (more elegant ?) would have been to use cargo update for
    # patched packages:
    #  cargo update --offline -p package_1 -p package_2
    # But this is not possible since it requires that cargo local git db
    # to be populated and this is not the case as we fetch git repo ourself.

    lockfile_orig = lockfile + ".orig"
    if not os.path.exists(lockfile_orig):
        shutil.copy(lockfile, lockfile_orig)

    newlines = []
    with open(lockfile_orig, "r") as f:
        for line in f.readlines():
            if not line.startswith("source = \"git"):
                newlines.append(line)

    with open(lockfile, "w") as f:
        f.writelines(newlines)
}
do_configure[postfuncs] += "egui_fix"

SRCREV_FORMAT = "radio_egui"
SRCREV_radio = "d9e41bf2520e3aaa36927f7cbf10b2067828454c"
SRCREV_egui = "b5627c7d4084c667d8ffefcd9131bfe5b6009e75"
