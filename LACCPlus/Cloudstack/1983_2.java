//,temp,AbstractStoragePoolAllocator.java,127,155,temp,ZoneWideStoragePoolAllocator.java,113,140
//,3
public class xxx {
    @Override
    protected List<StoragePool> reorderPoolsByNumberOfVolumes(DeploymentPlan plan, List<StoragePool> pools, Account account) {
        if (account == null) {
            return pools;
        }
        long dcId = plan.getDataCenterId();

        List<Long> poolIdsByVolCount = volumeDao.listZoneWidePoolIdsByVolumeCount(dcId, account.getAccountId());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("List of pools in ascending order of number of volumes for account id: " + account.getAccountId() + " is: " + poolIdsByVolCount);
        }

        // now filter the given list of Pools by this ordered list
        Map<Long, StoragePool> poolMap = new HashMap<>();
        for (StoragePool pool : pools) {
            poolMap.put(pool.getId(), pool);
        }
        List<Long> matchingPoolIds = new ArrayList<>(poolMap.keySet());

        poolIdsByVolCount.retainAll(matchingPoolIds);

        List<StoragePool> reorderedPools = new ArrayList<>();
        for (Long id : poolIdsByVolCount) {
            reorderedPools.add(poolMap.get(id));
        }

        return reorderedPools;
    }

};