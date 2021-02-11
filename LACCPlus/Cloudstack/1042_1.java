//,temp,VolumeApiServiceImpl.java,1356,1365,temp,VolumeApiServiceImpl.java,1343,1350
//,3
public class xxx {
    protected void expungeVolumesInSecondaryStorageIfNeeded(VolumeVO volume) throws InterruptedException, ExecutionException {
        VolumeInfo volOnSecondary = volFactory.getVolume(volume.getId(), DataStoreRole.Image);
        if (volOnSecondary != null) {
            s_logger.info("Expunging volume " + volume.getId() + " from secondary data store");
            AsyncCallFuture<VolumeApiResult> future2 = volService.expungeVolumeAsync(volOnSecondary);
            future2.get();

            _resourceLimitMgr.decrementResourceCount(volOnSecondary.getAccountId(), ResourceType.secondary_storage, volOnSecondary.getSize());
        }
    }

};