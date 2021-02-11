//,temp,XenServerStorageMotionStrategy.java,264,307,temp,XenServerStorageMotionStrategy.java,162,206
//,3
public class xxx {
    private String handleManagedVolumePreMigration(VolumeInfo volumeInfo, StoragePool storagePool, Host destHost) {
        final PrimaryDataStoreDriver pdsd = (PrimaryDataStoreDriver)volumeInfo.getDataStore().getDriver();

        VolumeDetailVO volumeDetailVo = new VolumeDetailVO(volumeInfo.getId(), PrimaryDataStoreDriver.BASIC_CREATE, Boolean.TRUE.toString(), false);

        volumeDetailsDao.persist(volumeDetailVo);

        pdsd.createAsync(volumeInfo.getDataStore(), volumeInfo, null);

        volumeDetailVo = new VolumeDetailVO(volumeInfo.getId(), PrimaryDataStoreDriver.BASIC_GRANT_ACCESS, Boolean.TRUE.toString(), false);

        volumeDetailsDao.persist(volumeDetailVo);

        pdsd.grantAccess(volumeInfo, destHost, volumeInfo.getDataStore());

        final Map<String, String> details = new HashMap<>();

        final String iqn = getBasicIqn(volumeInfo.getId());

        details.put(CreateStoragePoolCommand.DATASTORE_NAME, iqn);

        details.put(CreateStoragePoolCommand.IQN, iqn);

        details.put(CreateStoragePoolCommand.STORAGE_HOST, storagePool.getHostAddress());

        details.put(CreateStoragePoolCommand.STORAGE_PORT, String.valueOf(storagePool.getPort()));

        final CreateStoragePoolCommand cmd = new CreateStoragePoolCommand(true, storagePool);

        cmd.setDetails(details);
        cmd.setCreateDatastore(true);

        final Answer answer = agentMgr.easySend(destHost.getId(), cmd);

        if (answer == null || !answer.getResult()) {
            String errMsg = "Error interacting with host (related to CreateStoragePoolCommand)" +
                    (StringUtils.isNotBlank(answer.getDetails()) ? ": " + answer.getDetails() : "");

            s_logger.error(errMsg);

            throw new CloudRuntimeException(errMsg);
        }

        return iqn;
    }

};