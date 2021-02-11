//,temp,SolidFirePrimaryDataStoreDriver.java,188,226,temp,SolidFirePrimaryDataStoreDriver.java,151,186
//,3
public class xxx {
    @Override
    public void revokeAccess(DataObject dataObject, Host host, DataStore dataStore)
    {
        if (dataObject == null || host == null || dataStore == null) {
            return;
        }

        long sfVolumeId = getSolidFireVolumeId(dataObject, false);
        long clusterId = host.getClusterId();
        long storagePoolId = dataStore.getId();

        ClusterVO cluster = clusterDao.findById(clusterId);

        GlobalLock lock = GlobalLock.getInternLock(cluster.getUuid());

        if (!lock.lock(SolidFireUtil.LOCK_TIME_IN_SECONDS)) {
            String errMsg = "Couldn't lock the DB (in revokeAccess) on the following string: " + cluster.getUuid();

            LOGGER.warn(errMsg);

            throw new CloudRuntimeException(errMsg);
        }

        try {
            SolidFireUtil.SolidFireConnection sfConnection = SolidFireUtil.getSolidFireConnection(storagePoolId, storagePoolDetailsDao);

            List<SolidFireUtil.SolidFireVag> sfVags = SolidFireUtil.getAllVags(sfConnection);

            for (SolidFireUtil.SolidFireVag sfVag : sfVags) {
                if (SolidFireUtil.sfVagContains(sfVag, sfVolumeId, clusterId, hostDao)) {
                    SolidFireUtil.removeVolumeIdsFromSolidFireVag(sfConnection, sfVag.getId(), new Long[] { sfVolumeId });
                }
            }
        }
        finally {
            lock.unlock();
            lock.releaseRef();
        }
    }

};