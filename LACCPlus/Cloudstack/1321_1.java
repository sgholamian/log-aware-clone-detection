//,temp,UsageManagerImpl.java,1600,1635,temp,UsageManagerImpl.java,1557,1598
//,3
public class xxx {
    private void createSnapshotHelperEvent(UsageEventVO event) {
        long snapSize = -1L;
        long zoneId = -1L;

        long snapId = event.getResourceId();
        if (EventTypes.EVENT_SNAPSHOT_CREATE.equals(event.getType())) {
            if (usageSnapshotSelection){
                snapSize =  event.getVirtualSize();
            }else {
                snapSize = event.getSize();
            }
            zoneId = event.getZoneId();
        }

        if (EventTypes.EVENT_SNAPSHOT_CREATE.equals(event.getType())) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("create snapshot with id : " + snapId + " for account: " + event.getAccountId());
            }
            Account acct = _accountDao.findByIdIncludingRemoved(event.getAccountId());
            UsageStorageVO storageVO =
                    new UsageStorageVO(snapId, zoneId, event.getAccountId(), acct.getDomainId(), StorageTypes.SNAPSHOT, null, snapSize, event.getCreateDate(), null);
            _usageStorageDao.persist(storageVO);
        } else if (EventTypes.EVENT_SNAPSHOT_DELETE.equals(event.getType())) {
            List<UsageStorageVO> storageVOs = _usageStorageDao.listById(event.getAccountId(), snapId, StorageTypes.SNAPSHOT);
            if (storageVOs.size() > 1) {
                s_logger.warn("More that one usage entry for storage: " + snapId + " assigned to account: " + event.getAccountId() + "; marking them all as deleted...");
            }
            for (UsageStorageVO storageVO : storageVOs) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("deleting snapshot: " + storageVO.getId() + " from account: " + storageVO.getAccountId());
                }
                storageVO.setDeleted(event.getCreateDate()); // there really shouldn't be more than one
                _usageStorageDao.update(storageVO);
            }
        }
    }

};