//,temp,LibvirtStorageAdaptor.java,817,891,temp,KVMStorageProcessor.java,1609,1664
//,3
public class xxx {
    @Override
    public boolean deletePhysicalDisk(String uuid, KVMStoragePool pool, Storage.ImageFormat format) {

        s_logger.info("Attempting to remove volume " + uuid + " from pool " + pool.getUuid());

        /**
         * RBD volume can have snapshots and while they exist libvirt
         * can't remove the RBD volume
         *
         * We have to remove those snapshots first
         */
        if (pool.getType() == StoragePoolType.RBD) {
            try {
                s_logger.info("Unprotecting and Removing RBD snapshots of image " + pool.getSourceDir() + "/" + uuid + " prior to removing the image");

                Rados r = new Rados(pool.getAuthUserName());
                r.confSet("mon_host", pool.getSourceHost() + ":" + pool.getSourcePort());
                r.confSet("key", pool.getAuthSecret());
                r.confSet("client_mount_timeout", "30");
                r.connect();
                s_logger.debug("Succesfully connected to Ceph cluster at " + r.confGet("mon_host"));

                IoCTX io = r.ioCtxCreate(pool.getSourceDir());
                Rbd rbd = new Rbd(io);
                RbdImage image = rbd.open(uuid);
                s_logger.debug("Fetching list of snapshots of RBD image " + pool.getSourceDir() + "/" + uuid);
                List<RbdSnapInfo> snaps = image.snapList();
                try {
                    for (RbdSnapInfo snap : snaps) {
                        if (image.snapIsProtected(snap.name)) {
                            s_logger.debug("Unprotecting snapshot " + pool.getSourceDir() + "/" + uuid + "@" + snap.name);
                            image.snapUnprotect(snap.name);
                        } else {
                            s_logger.debug("Snapshot " + pool.getSourceDir() + "/" + uuid + "@" + snap.name + " is not protected.");
                        }
                        s_logger.debug("Removing snapshot " + pool.getSourceDir() + "/" + uuid + "@" + snap.name);
                        image.snapRemove(snap.name);
                    }
                    s_logger.info("Succesfully unprotected and removed any remaining snapshots (" + snaps.size() + ") of "
                        + pool.getSourceDir() + "/" + uuid + " Continuing to remove the RBD image");
                } catch (RbdException e) {
                    s_logger.error("Failed to remove snapshot with exception: " + e.toString() +
                        ", RBD error: " + ErrorCode.getErrorMessage(e.getReturnValue()));
                    throw new CloudRuntimeException(e.toString() + " - " + ErrorCode.getErrorMessage(e.getReturnValue()));
                } finally {
                    s_logger.debug("Closing image and destroying context");
                    rbd.close(image);
                    r.ioCtxDestroy(io);
                }
            } catch (RadosException e) {
                s_logger.error("Failed to remove snapshot with exception: " + e.toString() +
                    ", RBD error: " + ErrorCode.getErrorMessage(e.getReturnValue()));
                throw new CloudRuntimeException(e.toString() + " - " + ErrorCode.getErrorMessage(e.getReturnValue()));
            } catch (RbdException e) {
                s_logger.error("Failed to remove snapshot with exception: " + e.toString() +
                    ", RBD error: " + ErrorCode.getErrorMessage(e.getReturnValue()));
                throw new CloudRuntimeException(e.toString() + " - " + ErrorCode.getErrorMessage(e.getReturnValue()));
            }
        }

        LibvirtStoragePool libvirtPool = (LibvirtStoragePool)pool;
        try {
            StorageVol vol = getVolume(libvirtPool.getPool(), uuid);
            s_logger.debug("Instructing libvirt to remove volume " + uuid + " from pool " + pool.getUuid());
            if(Storage.ImageFormat.DIR.equals(format)){
                deleteDirVol(libvirtPool, vol);
            } else {
                deleteVol(libvirtPool, vol);
            }
            vol.free();
            return true;
        } catch (LibvirtException e) {
            throw new CloudRuntimeException(e.toString());
        }
    }

};