//,temp,KVMStorageProcessor.java,1326,1354,temp,KVMStorageProcessor.java,1295,1324
//,3
public class xxx {
    @Override
    public Answer attachVolume(final AttachCommand cmd) {
        final DiskTO disk = cmd.getDisk();
        final VolumeObjectTO vol = (VolumeObjectTO)disk.getData();
        final PrimaryDataStoreTO primaryStore = (PrimaryDataStoreTO)vol.getDataStore();
        final String vmName = cmd.getVmName();
        final String serial = resource.diskUuidToSerial(vol.getUuid());
        try {
            final Connect conn = LibvirtConnection.getConnectionByVmName(vmName);

            storagePoolMgr.connectPhysicalDisk(primaryStore.getPoolType(), primaryStore.getUuid(), vol.getPath(), disk.getDetails());

            final KVMPhysicalDisk phyDisk = storagePoolMgr.getPhysicalDisk(primaryStore.getPoolType(), primaryStore.getUuid(), vol.getPath());

            attachOrDetachDisk(conn, true, vmName, phyDisk, disk.getDiskSeq().intValue(), serial,
                    vol.getBytesReadRate(), vol.getBytesReadRateMax(), vol.getBytesReadRateMaxLength(),
                    vol.getBytesWriteRate(), vol.getBytesWriteRateMax(), vol.getBytesWriteRateMaxLength(),
                    vol.getIopsReadRate(), vol.getIopsReadRateMax(), vol.getIopsReadRateMaxLength(),
                    vol.getIopsWriteRate(), vol.getIopsWriteRateMax(), vol.getIopsWriteRateMaxLength());

            return new AttachAnswer(disk);
        } catch (final LibvirtException e) {
            s_logger.debug("Failed to attach volume: " + vol.getPath() + ", due to ", e);
            storagePoolMgr.disconnectPhysicalDisk(primaryStore.getPoolType(), primaryStore.getUuid(), vol.getPath());
            return new AttachAnswer(e.toString());
        } catch (final InternalErrorException e) {
            s_logger.debug("Failed to attach volume: " + vol.getPath() + ", due to ", e);
            return new AttachAnswer(e.toString());
        }
    }

};