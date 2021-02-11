//,temp,XenServerStorageMotionStrategy.java,419,454,temp,VmwareStorageMotionStrategy.java,350,376
//,3
public class xxx {
    private void updateVolumesAfterMigration(Map<VolumeInfo, DataStore> volumeToPool, List<VolumeObjectTO> volumeTos) {
        for (Map.Entry<VolumeInfo, DataStore> entry : volumeToPool.entrySet()) {
            boolean updated = false;
            VolumeInfo volume = entry.getKey();
            StoragePool pool = (StoragePool) entry.getValue();
            for (VolumeObjectTO volumeTo : volumeTos) {
                if (volume.getId() == volumeTo.getId()) {
                    VolumeVO volumeVO = volDao.findById(volume.getId());
                    Long oldPoolId = volumeVO.getPoolId();
                    volumeVO.setPath(volumeTo.getPath());
                    if (volumeTo.getChainInfo() != null) {
                        volumeVO.setChainInfo(volumeTo.getChainInfo());
                    }
                    volumeVO.setLastPoolId(oldPoolId);
                    volumeVO.setFolder(pool.getPath());
                    volumeVO.setPodId(pool.getPodId());
                    volumeVO.setPoolId(pool.getId());
                    volDao.update(volume.getId(), volumeVO);
                    updated = true;
                    break;
                }
            }
            if (!updated) {
                s_logger.error("Volume path wasn't updated for volume " + volume + " after it was migrated.");
            }
        }
    }

};