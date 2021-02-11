//,temp,SolidFireUtil.java,460,510,temp,SolidFireUtil.java,410,458
//,3
public class xxx {
    public static void hostAddedToCluster(long hostId, long clusterId, String storageProvider, ClusterDao clusterDao, HostDao hostDao,
                                          PrimaryDataStoreDao storagePoolDao, StoragePoolDetailsDao storagePoolDetailsDao) {
        HostVO hostVO = hostDao.findById(hostId);

        Preconditions.checkArgument(hostVO != null, "Could not locate host for ID: " + hostId);

        ClusterVO cluster = clusterDao.findById(clusterId);

        GlobalLock lock = GlobalLock.getInternLock(cluster.getUuid());

        if (!lock.lock(LOCK_TIME_IN_SECONDS)) {
            String errMsg = "Couldn't lock the DB on the following string: " + cluster.getUuid();

            LOGGER.warn(errMsg);

            throw new CloudRuntimeException(errMsg);
        }

        try {
            List<StoragePoolVO> storagePools = storagePoolDao.findPoolsByProvider(storageProvider);

            if (storagePools != null && storagePools.size() > 0) {
                List<SolidFireUtil.SolidFireConnection> sfConnections = new ArrayList<>();

                for (StoragePoolVO storagePool : storagePools) {
                    if (!isStorageApplicableToZoneOrCluster(storagePool, clusterId, clusterDao)) {
                        continue;
                    }

                    SolidFireUtil.SolidFireConnection sfConnection = SolidFireUtil.getSolidFireConnection(storagePool.getId(), storagePoolDetailsDao);

                    if (!sfConnections.contains(sfConnection)) {
                        sfConnections.add(sfConnection);

                        List<SolidFireUtil.SolidFireVag> sfVags = SolidFireUtil.getAllVags(sfConnection);
                        SolidFireVag sfVag = getVolumeAccessGroup(hostVO.getStorageUrl(), sfVags);

                        if (sfVag != null) {
                            placeVolumeIdsInVag(sfConnection, sfVags, sfVag, hostVO, hostDao);
                        } else {
                            handleVagForHost(sfConnection, sfVags, hostVO, hostDao);
                        }
                    }
                }
            }
        }
        finally {
            lock.unlock();
            lock.releaseRef();
        }
    }

};