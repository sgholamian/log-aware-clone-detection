//,temp,KVMStorageProcessor.java,1326,1354,temp,KVMStorageProcessor.java,1295,1324
//,3
public class xxx {
    @Override
    public Answer dettachVolume(final DettachCommand cmd) {
        final DiskTO disk = cmd.getDisk();
        final VolumeObjectTO vol = (VolumeObjectTO)disk.getData();
        final PrimaryDataStoreTO primaryStore = (PrimaryDataStoreTO)vol.getDataStore();
        final String vmName = cmd.getVmName();
        final String serial = resource.diskUuidToSerial(vol.getUuid());
        try {
            final Connect conn = LibvirtConnection.getConnectionByVmName(vmName);

            final KVMPhysicalDisk phyDisk = storagePoolMgr.getPhysicalDisk(primaryStore.getPoolType(), primaryStore.getUuid(), vol.getPath());

            attachOrDetachDisk(conn, false, vmName, phyDisk, disk.getDiskSeq().intValue(), serial,
                    vol.getBytesReadRate(), vol.getBytesReadRateMax(), vol.getBytesReadRateMaxLength(),
                    vol.getBytesWriteRate(), vol.getBytesWriteRateMax(), vol.getBytesWriteRateMaxLength(),
                    vol.getIopsReadRate(), vol.getIopsReadRateMax(), vol.getIopsReadRateMaxLength(),
                    vol.getIopsWriteRate(), vol.getIopsWriteRateMax(), vol.getIopsWriteRateMaxLength());

            storagePoolMgr.disconnectPhysicalDisk(primaryStore.getPoolType(), primaryStore.getUuid(), vol.getPath());

            return new DettachAnswer(disk);
        } catch (final LibvirtException e) {
            s_logger.debug("Failed to detach volume: " + vol.getPath() + ", due to ", e);
            return new DettachAnswer(e.toString());
        } catch (final InternalErrorException e) {
            s_logger.debug("Failed to detach volume: " + vol.getPath() + ", due to ", e);
            return new DettachAnswer(e.toString());
        }
    }

};