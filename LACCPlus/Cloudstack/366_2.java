//,temp,SolidFireSharedPrimaryDataStoreLifeCycle.java,381,432,temp,ElastistorPrimaryDataStoreLifeCycle.java,353,396
//,3
public class xxx {
    @Override
    public boolean attachCluster(DataStore store, ClusterScope scope) {

        dataStoreHelper.attachCluster(store);

        StoragePoolVO dataStoreVO = _storagePoolDao.findById(store.getId());

        PrimaryDataStoreInfo primarystore = (PrimaryDataStoreInfo) store;
        // Check if there is host up in this cluster
        List<HostVO> allHosts = _resourceMgr.listAllUpHosts(Host.Type.Routing, primarystore.getClusterId(), primarystore.getPodId(), primarystore.getDataCenterId());
        if (allHosts.isEmpty()) {
            primaryDataStoreDao.expunge(primarystore.getId());
            throw new CloudRuntimeException("No host up to associate a storage pool with in cluster " + primarystore.getClusterId());
        }

        if (!dataStoreVO.isManaged()) {
            boolean success = false;
            for (HostVO h : allHosts) {
                success = createStoragePool(h.getId(), primarystore);
                if (success) {
                    break;
                }
            }
        }

        s_logger.debug("In createPool Adding the pool to each of the hosts");
        List<HostVO> poolHosts = new ArrayList<HostVO>();
        for (HostVO h : allHosts) {
            try {
                storageMgr.connectHostToSharedPool(h.getId(), primarystore.getId());
                poolHosts.add(h);
            } catch (Exception e) {
                s_logger.warn("Unable to establish a connection between " + h + " and " + primarystore, e);
            }

            if (poolHosts.isEmpty()) {
                s_logger.warn("No host can access storage pool " + primarystore + " on cluster " + primarystore.getClusterId());
                primaryDataStoreDao.expunge(primarystore.getId());
                throw new CloudRuntimeException("Failed to access storage pool");
            }
        }

        return true;
    }

};