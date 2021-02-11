//,temp,VmwareStorageMotionStrategy.java,350,376,temp,HypervStorageMotionStrategy.java,145,176
//,3
public class xxx {
    private void updateVolumePathsAfterMigration(Map<VolumeInfo, DataStore> volumeToPool, List<VolumeObjectTO> volumeTos) {
        for (Map.Entry<VolumeInfo, DataStore> entry : volumeToPool.entrySet()) {
            boolean updated = false;
            VolumeInfo volume = entry.getKey();
            StoragePool pool = (StoragePool)entry.getValue();
            for (VolumeObjectTO volumeTo : volumeTos) {
                if (volume.getId() == volumeTo.getId()) {
                    VolumeVO volumeVO = volDao.findById(volume.getId());
                    Long oldPoolId = volumeVO.getPoolId();
                    volumeVO.setPath(volumeTo.getPath());
                    volumeVO.setPodId(pool.getPodId());
                    volumeVO.setPoolId(pool.getId());
                    volumeVO.setLastPoolId(oldPoolId);
                    // For SMB, pool credentials are also stored in the uri query string.  We trim the query string
                    // part  here to make sure the credentials do not get stored in the db unencrypted.
                    String folder = pool.getPath();
                    if (pool.getPoolType() == StoragePoolType.SMB && folder != null && folder.contains("?")) {
                        folder = folder.substring(0, folder.indexOf("?"));
                    }
                    volumeVO.setFolder(folder);

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