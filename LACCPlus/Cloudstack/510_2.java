//,temp,StorageSystemDataMotionStrategy.java,2696,2742,temp,StorageSystemDataMotionStrategy.java,2629,2694
//,3
public class xxx {
    private String migrateVolumeForKVM(VolumeInfo srcVolumeInfo, VolumeInfo destVolumeInfo, HostVO hostVO, String errMsg) {
        boolean srcVolumeDetached = srcVolumeInfo.getAttachedVM() == null;

        try {
            Map<String, String> srcDetails = getVolumeDetails(srcVolumeInfo);
            Map<String, String> destDetails = getVolumeDetails(destVolumeInfo);

            MigrateVolumeCommand migrateVolumeCommand = new MigrateVolumeCommand(srcVolumeInfo.getTO(), destVolumeInfo.getTO(),
                    srcDetails, destDetails, StorageManager.KvmStorageOfflineMigrationWait.value());

            if (srcVolumeDetached) {
                _volumeService.grantAccess(srcVolumeInfo, hostVO, srcVolumeInfo.getDataStore());
            }

            handleQualityOfServiceForVolumeMigration(destVolumeInfo, PrimaryDataStoreDriver.QualityOfServiceState.MIGRATION);

            _volumeService.grantAccess(destVolumeInfo, hostVO, destVolumeInfo.getDataStore());

            MigrateVolumeAnswer migrateVolumeAnswer = (MigrateVolumeAnswer)agentManager.send(hostVO.getId(), migrateVolumeCommand);

            if (migrateVolumeAnswer == null || !migrateVolumeAnswer.getResult()) {
                if (migrateVolumeAnswer != null && !StringUtils.isEmpty(migrateVolumeAnswer.getDetails())) {
                    throw new CloudRuntimeException(migrateVolumeAnswer.getDetails());
                }
                else {
                    throw new CloudRuntimeException(errMsg);
                }
            }

            if (srcVolumeDetached) {
                _volumeService.revokeAccess(destVolumeInfo, hostVO, destVolumeInfo.getDataStore());
            }

            try {
                _volumeService.revokeAccess(srcVolumeInfo, hostVO, srcVolumeInfo.getDataStore());
            }
            catch (Exception e) {
                // This volume should be deleted soon, so just log a warning here.
                LOGGER.warn(e.getMessage(), e);
            }

            return migrateVolumeAnswer.getVolumePath();
        }
        catch (Exception ex) {
            try {
                _volumeService.revokeAccess(destVolumeInfo, hostVO, destVolumeInfo.getDataStore());
            }
            catch (Exception e) {
                // This volume should be deleted soon, so just log a warning here.
                LOGGER.warn(e.getMessage(), e);
            }

            if (srcVolumeDetached) {
                _volumeService.revokeAccess(srcVolumeInfo, hostVO, srcVolumeInfo.getDataStore());
            }

            String msg = "Failed to perform volume migration : ";

            LOGGER.warn(msg, ex);

            throw new CloudRuntimeException(msg + ex.getMessage(), ex);
        }
        finally {
            handleQualityOfServiceForVolumeMigration(destVolumeInfo, PrimaryDataStoreDriver.QualityOfServiceState.NO_MIGRATION);
        }
    }

};