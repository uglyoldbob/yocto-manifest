#
# SPDX-License-Identifier: GPL-2.0-only
#
# DESCRIPTION
# This implements the 'isoimage-isohybrid' source plugin class for 'wic'
#
# AUTHORS
# Mihaly Varga <mihaly.varga (at] ni.com>

import glob
import logging
import os
import re
import shutil

from wic import WicError
from wic.engine import get_custom_config
from wic.pluginbase import SourcePlugin
from wic.misc import exec_cmd, exec_native_cmd, get_bitbake_var

logger = logging.getLogger('wic')

class PlainIsoImagePlugin(SourcePlugin):
    """
    Create a bootable ISO image

    This plugin creates a hybrid, legacy and EFI bootable ISO image. The
    generated image can be used on optical media as well as USB media.

    Legacy boot uses syslinux and EFI boot uses grub or gummiboot (not
    implemented yet) as bootloader. The plugin creates the directories required
    by bootloaders and populates them by creating and configuring the
    bootloader files.

    Example kickstart file:
    part /boot --source isoimage-isohybrid --sourceparams="loader=grub-efi, \\
    image_name= IsoImage" --ondisk cd --label LIVECD
    bootloader  --timeout=10  --append=" "

    In --sourceparams "loader" specifies the bootloader used for booting in EFI
    mode, while "image_name" specifies the name of the generated image. In the
    example above, wic creates an ISO image named IsoImage-cd.direct (default
    extension added by direct imeger plugin) and a file named IsoImage-cd.iso
    """

    name = 'iso'

    @classmethod
    def do_configure_partition(cls, part, source_params, creator, cr_workdir,
                               oe_builddir, bootimg_dir, kernel_dir,
                               native_sysroot):
        """
        Called before do_prepare_partition(), creates loader-specific config
        """
        isodir = "%s/ISO/" % cr_workdir

        if os.path.exists(isodir):
            shutil.rmtree(isodir)

        install_cmd = "install -d %s " % isodir
        exec_cmd(install_cmd)

        # Overwrite the name of the created image
        logger.debug(source_params)
        if 'image_name' in source_params and \
                    source_params['image_name'].strip():
            creator.name = source_params['image_name'].strip()
            logger.debug("The name of the image is: %s", creator.name)

    @classmethod
    def do_prepare_partition(cls, part, source_params, creator, cr_workdir,
                             oe_builddir, bootimg_dir, kernel_dir,
                             rootfs_dir, native_sysroot):
        """
        Called to do the actual content population for a partition i.e. it
        'prepares' the partition to be incorporated into the image.
        In this case, prepare content for a bootable ISO image.
        """

        isodir = "%s/ISO" % cr_workdir

        if part.rootfs_dir is None:
            if not 'ROOTFS_DIR' in rootfs_dir:
                raise WicError("Couldn't find --rootfs-dir, exiting.")
            rootfs_dir = rootfs_dir['ROOTFS_DIR']
        else:
            if part.rootfs_dir in rootfs_dir:
                rootfs_dir = rootfs_dir[part.rootfs_dir]
            elif part.rootfs_dir:
                rootfs_dir = part.rootfs_dir
            else:
                raise WicError("Couldn't find --rootfs-dir=%s connection "
                               "or it is not a valid path, exiting." %
                               part.rootfs_dir)

        if not os.path.isdir(rootfs_dir):
            rootfs_dir = get_bitbake_var("IMAGE_ROOTFS")
        if not os.path.isdir(rootfs_dir):
            raise WicError("Couldn't find IMAGE_ROOTFS, exiting.")

        part.rootfs_dir = rootfs_dir
        deploy_dir = get_bitbake_var("DEPLOY_DIR_IMAGE")
        img_iso_dir = get_bitbake_var("ISODIR")

        # Remove the temporary file created by part.prepare_rootfs()
        if os.path.isfile(part.source_file):
            os.remove(part.source_file)

        logger.warning("I am groot\n")

        isodir = rootfs_dir

        #create ISO image
        iso_img = "%s/tempiso_img.iso" % cr_workdir
        iso_bootimg = "isolinux/isolinux.bin"
        iso_bootcat = "isolinux/boot.cat"
        efi_img = "efi.img"

        mkisofs_cmd = "mkisofs -V %s " % part.label
        mkisofs_cmd += "-o %s -U " % iso_img
        mkisofs_cmd += "-J -joliet-long -r -iso-level 2 -b %s " % iso_bootimg
        mkisofs_cmd += "-c %s -no-emul-boot -boot-load-size 4 " % iso_bootcat
        mkisofs_cmd += "-boot-info-table -eltorito-alt-boot "
        mkisofs_cmd += "-eltorito-platform 0xEF -eltorito-boot %s " % efi_img
        mkisofs_cmd += "-no-emul-boot %s " % isodir

        mkisofs_cmd = "mkisofs -V %s " % part.label
        mkisofs_cmd += "-o %s -U " % iso_img
        mkisofs_cmd += "-J -joliet-long -r -iso-level 2 %s" % isodir
        logger.debug("I am groot\n")


        exec_native_cmd("ls -la %s > test.txt" % isodir, native_sysroot)

        logger.warning("running command: %s", mkisofs_cmd)
        exec_native_cmd(mkisofs_cmd, native_sysroot)

        raise WicError("Just check to see if you are there. I am groot %s " % isodir)



        shutil.rmtree(isodir)

        du_cmd = "du -Lbks %s" % iso_img
        out = exec_cmd(du_cmd)
        isoimg_size = int(out.split()[0])

        part.size = isoimg_size
        part.source_file = iso_img

    @classmethod
    def do_install_disk(cls, disk, disk_name, creator, workdir, oe_builddir,
                        bootimg_dir, kernel_dir, native_sysroot):
        """
        Called after all partitions have been prepared and assembled into a
        disk image.  In this case, we insert/modify the MBR using isohybrid
        utility for booting via BIOS from disk storage devices.
        """

        iso_img = "%s.p1" % disk.path
        full_path = creator._full_path(workdir, disk_name, "direct")
        full_path_iso = creator._full_path(workdir, disk_name, "iso")

        isohybrid_cmd = "isohybrid -u %s" % iso_img
        logger.debug("running command: %s", isohybrid_cmd)
        exec_native_cmd(isohybrid_cmd, native_sysroot)

        # Replace the image created by direct plugin with the one created by
        # mkisofs command. This is necessary because the iso image created by
        # mkisofs has a very specific MBR is system area of the ISO image, and
        # direct plugin adds and configures an another MBR.
        logger.debug("Replaceing the image created by direct plugin\n")
        os.remove(disk.path)
        shutil.copy2(iso_img, full_path_iso)
        shutil.copy2(full_path_iso, full_path)
