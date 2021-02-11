//,temp,sample_2665.java,2,17,temp,sample_2664.java,2,17
//,2
public class xxx {
public void dummy_method(){
List<StoragePoolVO> storagePools = _storagePoolDao.listAll();
for (StoragePoolVO pool : storagePools) {
long disk = _capacityMgr.getAllocatedPoolCapacity(pool, null);
if (pool.isShared()) {
_storageMgr.createCapacityEntry(pool, Capacity.CAPACITY_TYPE_STORAGE_ALLOCATED, disk);
} else {
_storageMgr.createCapacityEntry(pool, Capacity.CAPACITY_TYPE_LOCAL_STORAGE, disk);
}
}
if (s_logger.isDebugEnabled()) {


log.info("done executing storage capacity update");
}
}

};