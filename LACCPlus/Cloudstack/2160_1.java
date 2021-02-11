//,temp,XenServerStorageMotionStrategy.java,264,307,temp,XenServerStorageMotionStrategy.java,226,262
//,3
public class xxx {
    private void handleManagedVolumesAfterFailedMigration(Map<VolumeInfo, DataStore> volumeToPool, Host destHost) {
        for (Map.Entry<VolumeInfo, DataStore> entry : volumeToPool.entrySet()) {
            VolumeInfo volumeInfo = entry.getKey();
            StoragePool storagePool = storagePoolDao.findById(volumeInfo.getPoolId());

            if (storagePool.isManaged()) {
                final Map<String, String> details = new HashMap<>();

                details.put(DeleteStoragePoolCommand.DATASTORE_NAME, getBasicIqn(volumeInfo.getId()));

                final DeleteStoragePoolCommand cmd = new DeleteStoragePoolCommand();

                cmd.setDetails(details);
                cmd.setRemoveDatastore(true);

                final Answer answer = agentMgr.easySend(destHost.getId(), cmd);

                if (answer == null || !answer.getResult()) {
                    String errMsg = "Error interacting with host (related to handleManagedVolumesAfterFailedMigration)" +
                            (StringUtils.isNotBlank(answer.getDetails()) ? ": " + answer.getDetails() : "");

                    s_logger.error(errMsg);

                    // no need to throw an exception here as the calling code is responsible for doing so
                    // regardless of the success or lack thereof concerning this method
                    return;
                }

                final PrimaryDataStoreDriver pdsd = (PrimaryDataStoreDriver)volumeInfo.getDataStore().getDriver();

                VolumeDetailVO volumeDetailVo = new VolumeDetailVO(volumeInfo.getId(), PrimaryDataStoreDriver.BASIC_REVOKE_ACCESS, Boolean.TRUE.toString(), false);

                volumeDetailsDao.persist(volumeDetailVo);

                pdsd.revokeAccess(volumeInfo, destHost, volumeInfo.getDataStore());

                volumeDetailVo = new VolumeDetailVO(volumeInfo.getId(), PrimaryDataStoreDriver.BASIC_DELETE_FAILURE, Boolean.TRUE.toString(), false);

                volumeDetailsDao.persist(volumeDetailVo);

                pdsd.deleteAsync(volumeInfo.getDataStore(), volumeInfo, null);
            }
        }
    }

};