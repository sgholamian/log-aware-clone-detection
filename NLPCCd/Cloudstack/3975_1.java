//,temp,sample_3410.java,2,16,temp,sample_3411.java,2,16
//,2
public class xxx {
public void createCapacityEntry(StoragePoolVO storagePool, short capacityType, long allocated) {
SearchCriteria<CapacityVO> capacitySC = _capacityDao.createSearchCriteria();
capacitySC.addAnd("hostOrPoolId", SearchCriteria.Op.EQ, storagePool.getId());
capacitySC.addAnd("dataCenterId", SearchCriteria.Op.EQ, storagePool.getDataCenterId());
capacitySC.addAnd("capacityType", SearchCriteria.Op.EQ, capacityType);
List<CapacityVO> capacities = _capacityDao.search(capacitySC, null);
long totalOverProvCapacity;
if (storagePool.getPoolType() == StoragePoolType.NetworkFilesystem || storagePool.getPoolType() == StoragePoolType.VMFS) {
BigDecimal overProvFactor = getStorageOverProvisioningFactor(storagePool.getId());
totalOverProvCapacity = overProvFactor.multiply(new BigDecimal(storagePool.getCapacityBytes())).longValue();


log.info("found storage pool of type with overprovisioning factor");
}
}

};