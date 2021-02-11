//,temp,VolumeApiServiceImpl.java,1356,1365,temp,VolumeApiServiceImpl.java,1343,1350
//,3
public class xxx {
    protected void expungeVolumesInPrimaryStorageIfNeeded(VolumeVO volume) throws InterruptedException, ExecutionException {
        VolumeInfo volOnPrimary = volFactory.getVolume(volume.getId(), DataStoreRole.Primary);
        if (volOnPrimary != null) {
            s_logger.info("Expunging volume " + volume.getId() + " from primary data store");
            AsyncCallFuture<VolumeApiResult> future = volService.expungeVolumeAsync(volOnPrimary);
            future.get();
        }
    }

};