//,temp,XenServerStorageMotionStrategy.java,419,454,temp,VmwareStorageMotionStrategy.java,350,376
//,3
public class xxx {
    private void updateVolumePathsAfterMigration(Map<VolumeInfo, DataStore> volumeToPool, List<VolumeObjectTO> volumeTos, Host srcHost) {
        for (Map.Entry<VolumeInfo, DataStore> entry : volumeToPool.entrySet()) {
            VolumeInfo volumeInfo = entry.getKey();
            StoragePool storagePool = (StoragePool)entry.getValue();

            boolean updated = false;

            for (VolumeObjectTO volumeTo : volumeTos) {
                if (volumeInfo.getId() == volumeTo.getId()) {
                    if (storagePool.isManaged()) {
                        handleManagedVolumePostMigration(volumeInfo, srcHost, volumeTo);
                    }
                    else {
                        VolumeVO volumeVO = volDao.findById(volumeInfo.getId());
                        Long oldPoolId = volumeVO.getPoolId();

                        volumeVO.setPath(volumeTo.getPath());
                        volumeVO.setFolder(storagePool.getPath());
                        volumeVO.setPodId(storagePool.getPodId());
                        volumeVO.setPoolId(storagePool.getId());
                        volumeVO.setLastPoolId(oldPoolId);

                        volDao.update(volumeInfo.getId(), volumeVO);
                    }

                    updated = true;

                    break;
                }
            }

            if (!updated) {
                s_logger.error("The volume path wasn't updated for volume '" + volumeInfo + "' after it was migrated.");
            }
        }
    }

};