//,temp,LibvirtStorageAdaptor.java,817,891,temp,KVMStorageProcessor.java,1609,1664
//,3
public class xxx {
    @Override
    public Answer deleteSnapshot(final DeleteCommand cmd) {
        String snap_full_name = "";
        try {
            SnapshotObjectTO snapshotTO = (SnapshotObjectTO) cmd.getData();
            PrimaryDataStoreTO primaryStore = (PrimaryDataStoreTO) snapshotTO.getDataStore();
            VolumeObjectTO volume = snapshotTO.getVolume();
            KVMStoragePool primaryPool = storagePoolMgr.getStoragePool(primaryStore.getPoolType(), primaryStore.getUuid());
            KVMPhysicalDisk disk = storagePoolMgr.getPhysicalDisk(primaryStore.getPoolType(), primaryStore.getUuid(), volume.getPath());
            String snapshotFullPath = snapshotTO.getPath();
            String snapshotName = snapshotFullPath.substring(snapshotFullPath.lastIndexOf("/") + 1);
            snap_full_name = disk.getName() + "@" + snapshotName;
            if (primaryPool.getType() == StoragePoolType.RBD) {
                Rados r = new Rados(primaryPool.getAuthUserName());
                r.confSet("mon_host", primaryPool.getSourceHost() + ":" + primaryPool.getSourcePort());
                r.confSet("key", primaryPool.getAuthSecret());
                r.confSet("client_mount_timeout", "30");
                r.connect();
                s_logger.debug("Succesfully connected to Ceph cluster at " + r.confGet("mon_host"));
                IoCTX io = r.ioCtxCreate(primaryPool.getSourceDir());
                Rbd rbd = new Rbd(io);
                RbdImage image = rbd.open(disk.getName());
                try {
                    s_logger.info("Attempting to remove RBD snapshot " + snap_full_name);
                    if (image.snapIsProtected(snapshotName)) {
                        s_logger.debug("Unprotecting RBD snapshot " + snap_full_name);
                        image.snapUnprotect(snapshotName);
                    }
                    image.snapRemove(snapshotName);
                    s_logger.info("Snapshot " + snap_full_name + " successfully removed from " +
                            primaryPool.getType().toString() + "  pool.");
                } catch (RbdException e) {
                    s_logger.error("Failed to remove snapshot " + snap_full_name + ", with exception: " + e.toString() +
                        ", RBD error: " + ErrorCode.getErrorMessage(e.getReturnValue()));
                } finally {
                    rbd.close(image);
                    r.ioCtxDestroy(io);
                }
            } else {
                s_logger.warn("Operation not implemented for storage pool type of " + primaryPool.getType().toString());
                throw new InternalErrorException("Operation not implemented for storage pool type of " + primaryPool.getType().toString());
            }
            return new Answer(cmd, true, "Snapshot " + snap_full_name + " removed successfully.");
        } catch (RadosException e) {
            s_logger.error("Failed to remove snapshot " + snap_full_name + ", with exception: " + e.toString() +
                ", RBD error: " + ErrorCode.getErrorMessage(e.getReturnValue()));
            return new Answer(cmd, false, "Failed to remove snapshot " + snap_full_name);
        } catch (RbdException e) {
            s_logger.error("Failed to remove snapshot " + snap_full_name + ", with exception: " + e.toString() +
                ", RBD error: " + ErrorCode.getErrorMessage(e.getReturnValue()));
            return new Answer(cmd, false, "Failed to remove snapshot " + snap_full_name);
        } catch (Exception e) {
            s_logger.error("Failed to remove snapshot " + snap_full_name + ", with exception: " + e.toString());
            return new Answer(cmd, false, "Failed to remove snapshot " + snap_full_name);
        }
    }

};