//,temp,KVMStorageProcessor.java,1554,1607,temp,KVMStorageProcessor.java,406,477
//,3
public class xxx {
    @Override
    public Answer createVolumeFromSnapshot(final CopyCommand cmd) {
        try {
            final DataTO srcData = cmd.getSrcTO();
            final SnapshotObjectTO snapshot = (SnapshotObjectTO)srcData;
            final DataTO destData = cmd.getDestTO();
            final PrimaryDataStoreTO pool = (PrimaryDataStoreTO)destData.getDataStore();
            final DataStoreTO imageStore = srcData.getDataStore();
            final VolumeObjectTO volume = snapshot.getVolume();

            if (!(imageStore instanceof NfsTO)) {
                return new CopyCmdAnswer("unsupported protocol");
            }

            final NfsTO nfsImageStore = (NfsTO)imageStore;

            final String snapshotFullPath = snapshot.getPath();
            final int index = snapshotFullPath.lastIndexOf("/");
            final String snapshotPath = snapshotFullPath.substring(0, index);
            final String snapshotName = snapshotFullPath.substring(index + 1);
            final KVMStoragePool secondaryPool = storagePoolMgr.getStoragePoolByURI(nfsImageStore.getUrl() + File.separator + snapshotPath);
            final KVMPhysicalDisk snapshotDisk = secondaryPool.getPhysicalDisk(snapshotName);

            if (volume.getFormat() == ImageFormat.RAW) {
                snapshotDisk.setFormat(PhysicalDiskFormat.RAW);
            } else if (volume.getFormat() == ImageFormat.QCOW2) {
                snapshotDisk.setFormat(PhysicalDiskFormat.QCOW2);
            }

            final String primaryUuid = pool.getUuid();
            final KVMStoragePool primaryPool = storagePoolMgr.getStoragePool(pool.getPoolType(), primaryUuid);
            final String volUuid = UUID.randomUUID().toString();

            Map<String, String> details = cmd.getOptions2();

            String path = details != null ? details.get(DiskTO.IQN) : null;

            storagePoolMgr.connectPhysicalDisk(pool.getPoolType(), pool.getUuid(), path, details);

            KVMPhysicalDisk disk = storagePoolMgr.copyPhysicalDisk(snapshotDisk, path != null ? path : volUuid, primaryPool, cmd.getWaitInMillSeconds());

            storagePoolMgr.disconnectPhysicalDisk(pool.getPoolType(), pool.getUuid(), path);

            final VolumeObjectTO newVol = new VolumeObjectTO();
            newVol.setPath(disk.getName());
            newVol.setSize(disk.getVirtualSize());
            newVol.setFormat(ImageFormat.valueOf(disk.getFormat().toString().toUpperCase()));

            return new CopyCmdAnswer(newVol);
        } catch (final CloudRuntimeException e) {
            s_logger.debug("Failed to createVolumeFromSnapshot: ", e);
            return new CopyCmdAnswer(e.toString());
        }
    }

};