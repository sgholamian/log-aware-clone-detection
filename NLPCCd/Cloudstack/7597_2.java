//,temp,sample_8785.java,2,18,temp,sample_8789.java,2,18
//,2
public class xxx {
public void dummy_method(){
List<UsageStorageVO> storageVOs;
if (zoneId != -1L) {
storageVOs = _usageStorageDao.listByIdAndZone(event.getAccountId(), isoId, StorageTypes.ISO, zoneId);
} else {
storageVOs = _usageStorageDao.listById(event.getAccountId(), isoId, StorageTypes.ISO);
}
if (storageVOs.size() > 1) {
}
for (UsageStorageVO storageVO : storageVOs) {
if (s_logger.isDebugEnabled()) {


log.info("deleting iso from account");
}
}
}

};