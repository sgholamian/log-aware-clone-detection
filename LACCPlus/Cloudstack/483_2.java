//,temp,XenServerStorageMotionStrategy.java,226,262,temp,StorageSystemDataMotionStrategy.java,784,814
//,3
public class xxx {
    private void handleFailedVolumeMigration(VolumeInfo srcVolumeInfo, VolumeInfo destVolumeInfo, HostVO hostVO) {
        try {
            _volumeService.revokeAccess(destVolumeInfo, hostVO, destVolumeInfo.getDataStore());
        }
        catch (Exception ex) {
            LOGGER.warn("Failed to revoke access to the volume with the following ID: " + destVolumeInfo.getId());
        }

        try {
            VolumeDetailVO volumeDetailVO = new VolumeDetailVO(destVolumeInfo.getId(), PrimaryDataStoreDriver.BASIC_DELETE_BY_FOLDER,
                    Boolean.TRUE.toString(), false);

            volumeDetailsDao.persist(volumeDetailVO);

            destVolumeInfo.getDataStore().getDriver().deleteAsync(destVolumeInfo.getDataStore(), destVolumeInfo, null);

            volumeDetailsDao.removeDetails(srcVolumeInfo.getId());
        }
        catch (Exception ex) {
            LOGGER.warn(ex.getMessage());
        }

        VolumeVO volumeVO = _volumeDao.findById(srcVolumeInfo.getId());

        volumeVO.setPoolId(srcVolumeInfo.getPoolId());
        volumeVO.setLastPoolId(srcVolumeInfo.getLastPoolId());
        volumeVO.setFolder(srcVolumeInfo.getFolder());
        volumeVO.set_iScsiName(srcVolumeInfo.get_iScsiName());

        _volumeDao.update(srcVolumeInfo.getId(), volumeVO);
    }

};