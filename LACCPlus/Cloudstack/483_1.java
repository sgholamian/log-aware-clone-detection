//,temp,XenServerStorageMotionStrategy.java,226,262,temp,StorageSystemDataMotionStrategy.java,784,814
//,3
public class xxx {
    private void handleManagedVolumePostMigration(VolumeInfo volumeInfo, Host srcHost, VolumeObjectTO volumeTO) {
        final Map<String, String> details = new HashMap<>();

        details.put(DeleteStoragePoolCommand.DATASTORE_NAME, volumeInfo.get_iScsiName());

        final DeleteStoragePoolCommand cmd = new DeleteStoragePoolCommand();

        cmd.setDetails(details);
        cmd.setRemoveDatastore(true);

        final Answer answer = agentMgr.easySend(srcHost.getId(), cmd);

        if (answer == null || !answer.getResult()) {
            String errMsg = "Error interacting with host (related to DeleteStoragePoolCommand)" +
                    (StringUtils.isNotBlank(answer.getDetails()) ? ": " + answer.getDetails() : "");

            s_logger.error(errMsg);

            throw new CloudRuntimeException(errMsg);
        }

        final PrimaryDataStoreDriver pdsd = (PrimaryDataStoreDriver)volumeInfo.getDataStore().getDriver();

        pdsd.revokeAccess(volumeInfo, srcHost, volumeInfo.getDataStore());

        VolumeDetailVO volumeDetailVo = new VolumeDetailVO(volumeInfo.getId(), PrimaryDataStoreDriver.BASIC_DELETE, Boolean.TRUE.toString(), false);

        volumeDetailsDao.persist(volumeDetailVo);

        pdsd.deleteAsync(volumeInfo.getDataStore(), volumeInfo, null);

        VolumeVO volumeVO = volDao.findById(volumeInfo.getId());

        volumeVO.setPath(volumeTO.getPath());

        volDao.update(volumeVO.getId(), volumeVO);
    }

};