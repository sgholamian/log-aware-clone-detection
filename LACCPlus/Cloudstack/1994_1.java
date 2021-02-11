//,temp,RandomStoragePoolAllocator.java,37,79,temp,RandomAllocator.java,109,162
//,3
public class xxx {
    @Override
    public List<StoragePool> select(DiskProfile dskCh, VirtualMachineProfile vmProfile, DeploymentPlan plan, ExcludeList avoid, int returnUpTo) {

        List<StoragePool> suitablePools = new ArrayList<StoragePool>();

        long dcId = plan.getDataCenterId();
        Long podId = plan.getPodId();
        Long clusterId = plan.getClusterId();

        if (podId == null) {
            return null;
        }

        s_logger.debug("Looking for pools in dc: " + dcId + "  pod:" + podId + "  cluster:" + clusterId);
        List<StoragePoolVO> pools = storagePoolDao.listBy(dcId, podId, clusterId, ScopeType.CLUSTER);
        if (pools.size() == 0) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("No storage pools available for allocation, returning");
            }
            return suitablePools;
        }

        Collections.shuffle(pools);
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("RandomStoragePoolAllocator has " + pools.size() + " pools to check for allocation");
        }
        for (StoragePoolVO pool : pools) {
            if (suitablePools.size() == returnUpTo) {
                break;
            }
            StoragePool pol = (StoragePool)this.dataStoreMgr.getPrimaryDataStore(pool.getId());

            if (filter(avoid, pol, dskCh, plan)) {
                suitablePools.add(pol);
            }
        }

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("RandomStoragePoolAllocator returning " + suitablePools.size() + " suitable storage pools");
        }

        return suitablePools;
    }

};