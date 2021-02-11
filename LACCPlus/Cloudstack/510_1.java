//,temp,StorageSystemDataMotionStrategy.java,2696,2742,temp,StorageSystemDataMotionStrategy.java,2629,2694
//,3
public class xxx {
    private String copyManagedVolumeToSecondaryStorage(VolumeInfo srcVolumeInfo, VolumeInfo destVolumeInfo, HostVO hostVO, String errMsg) {
        boolean srcVolumeDetached = srcVolumeInfo.getAttachedVM() == null;

        try {
            StoragePoolVO storagePoolVO = _storagePoolDao.findById(srcVolumeInfo.getPoolId());
            Map<String, String> srcDetails = getVolumeDetails(srcVolumeInfo);

            CopyVolumeCommand copyVolumeCommand = new CopyVolumeCommand(srcVolumeInfo.getId(), destVolumeInfo.getPath(), storagePoolVO,
                    destVolumeInfo.getDataStore().getUri(), true, StorageManager.KvmStorageOfflineMigrationWait.value(), true);

            copyVolumeCommand.setSrcData(srcVolumeInfo.getTO());
            copyVolumeCommand.setSrcDetails(srcDetails);

            handleQualityOfServiceForVolumeMigration(srcVolumeInfo, PrimaryDataStoreDriver.QualityOfServiceState.MIGRATION);

            if (srcVolumeDetached) {
                _volumeService.grantAccess(srcVolumeInfo, hostVO, srcVolumeInfo.getDataStore());
            }

            CopyVolumeAnswer copyVolumeAnswer = (CopyVolumeAnswer)agentManager.send(hostVO.getId(), copyVolumeCommand);

            if (copyVolumeAnswer == null || !copyVolumeAnswer.getResult()) {
                if (copyVolumeAnswer != null && !StringUtils.isEmpty(copyVolumeAnswer.getDetails())) {
                    throw new CloudRuntimeException(copyVolumeAnswer.getDetails());
                }
                else {
                    throw new CloudRuntimeException(errMsg);
                }
            }

            return copyVolumeAnswer.getVolumePath();
        }
        catch (Exception ex) {
            String msg = "Failed to perform volume copy to secondary storage : ";

            LOGGER.warn(msg, ex);

            throw new CloudRuntimeException(msg + ex.getMessage());
        }
        finally {
            if (srcVolumeDetached) {
                _volumeService.revokeAccess(srcVolumeInfo, hostVO, srcVolumeInfo.getDataStore());
            }

            handleQualityOfServiceForVolumeMigration(srcVolumeInfo, PrimaryDataStoreDriver.QualityOfServiceState.NO_MIGRATION);
        }
    }

};