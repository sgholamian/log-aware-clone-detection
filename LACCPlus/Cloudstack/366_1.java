//,temp,SolidFireSharedPrimaryDataStoreLifeCycle.java,381,432,temp,ElastistorPrimaryDataStoreLifeCycle.java,353,396
//,3
public class xxx {
    @Override
    public boolean attachCluster(DataStore store, ClusterScope scope) {
        PrimaryDataStoreInfo primaryDataStoreInfo = (PrimaryDataStoreInfo)store;

        // check if there is at least one host up in this cluster
        List<HostVO> allHosts = resourceMgr.listAllUpHosts(Host.Type.Routing, primaryDataStoreInfo.getClusterId(),
                primaryDataStoreInfo.getPodId(), primaryDataStoreInfo.getDataCenterId());

        if (allHosts.isEmpty()) {
            primaryDataStoreDao.expunge(primaryDataStoreInfo.getId());

            throw new CloudRuntimeException("No host up to associate a storage pool with in cluster " + primaryDataStoreInfo.getClusterId());
        }

        boolean success = false;

        for (HostVO host : allHosts) {
            success = createStoragePool(host, primaryDataStoreInfo);

            if (success) {
                break;
            }
        }

        if (!success) {
            throw new CloudRuntimeException("Unable to create storage in cluster " + primaryDataStoreInfo.getClusterId());
        }

        List<HostVO> poolHosts = new ArrayList<>();

        for (HostVO host : allHosts) {
            try {
                storageMgr.connectHostToSharedPool(host.getId(), primaryDataStoreInfo.getId());

                poolHosts.add(host);
            } catch (Exception e) {
                LOGGER.warn("Unable to establish a connection between " + host + " and " + primaryDataStoreInfo, e);
            }
        }

        if (poolHosts.isEmpty()) {
            LOGGER.warn("No host can access storage pool '" + primaryDataStoreInfo + "' on cluster '" + primaryDataStoreInfo.getClusterId() + "'.");

            primaryDataStoreDao.expunge(primaryDataStoreInfo.getId());

            throw new CloudRuntimeException("Failed to access storage pool");
        }

        primaryDataStoreHelper.attachCluster(store);

        return true;
    }

};