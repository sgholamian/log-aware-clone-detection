//,temp,SolidFirePrimaryDataStoreDriver.java,188,226,temp,SolidFirePrimaryDataStoreDriver.java,151,186
//,3
public class xxx {
    @Override
    public boolean grantAccess(DataObject dataObject, Host host, DataStore dataStore) {
        Preconditions.checkArgument(dataObject != null, "'dataObject' should not be 'null'");
        Preconditions.checkArgument(host != null, "'host' should not be 'null'");
        Preconditions.checkArgument(dataStore != null, "'dataStore' should not be 'null'");

        long sfVolumeId = getSolidFireVolumeId(dataObject, true);
        long clusterId = host.getClusterId();
        long storagePoolId = dataStore.getId();

        ClusterVO cluster = clusterDao.findById(clusterId);

        GlobalLock lock = GlobalLock.getInternLock(cluster.getUuid());

        if (!lock.lock(SolidFireUtil.LOCK_TIME_IN_SECONDS)) {
            String errMsg = "Couldn't lock the DB (in grantAccess) on the following string: " + cluster.getUuid();

            LOGGER.warn(errMsg);

            throw new CloudRuntimeException(errMsg);
        }

        try {
            List<HostVO> hosts = hostDao.findByClusterId(clusterId);

            SolidFireUtil.SolidFireConnection sfConnection = SolidFireUtil.getSolidFireConnection(storagePoolId, storagePoolDetailsDao);

            SolidFireUtil.placeVolumeInVolumeAccessGroups(sfConnection, sfVolumeId, hosts);

            return true;
        }
        finally {
            lock.unlock();
            lock.releaseRef();
        }
    }

};