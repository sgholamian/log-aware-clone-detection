//,temp,CloudStackPrimaryDataStoreLifeCycleImpl.java,473,508,temp,ElastistorPrimaryDataStoreLifeCycle.java,477,514
//,3
public class xxx {
    @SuppressWarnings("finally")
    @Override
    public boolean deleteDataStore(DataStore store) {
        List<StoragePoolHostVO> hostPoolRecords = _storagePoolHostDao.listByPoolId(store.getId());
        StoragePool pool = (StoragePool) store;

        // find the hypervisor where the storage is attached to.
        HypervisorType hType = null;
        if (hostPoolRecords.size() > 0) {
            hType = getHypervisorType(hostPoolRecords.get(0).getHostId());
        }

        StoragePoolVO storagePoolVO = _storagePoolDao.findById(store.getId());

        if (!(storagePoolVO.isManaged())) {
            // Remove the SR associated with the Xenserver
            for (StoragePoolHostVO host : hostPoolRecords) {
                DeleteStoragePoolCommand deleteCmd = new DeleteStoragePoolCommand(pool);
                final Answer answer = agentMgr.easySend(host.getHostId(), deleteCmd);

                if (answer != null && answer.getResult()) {
                    // if host is KVM hypervisor then send deleteStoragepoolcmd
                    // to all the kvm hosts.
                    if (HypervisorType.KVM != hType) {
                        break;
                    }
                } else {
                    if (answer != null) {
                        s_logger.error("Failed to delete storage pool: " + answer.getResult());
                    }
                }
            }
        }
        // delete the Elastistor volume at backend
        deleteElastistorVolume(pool, storagePoolVO.isManaged());

        return _dataStoreHelper.deletePrimaryDataStore(store);
    }

};