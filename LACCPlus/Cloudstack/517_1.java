//,temp,UsageManagerImpl.java,1557,1598,temp,UsageManagerImpl.java,1502,1555
//,3
public class xxx {
    private void createISOHelperEvent(UsageEventVO event) {
        long isoSize = -1L;

        long isoId = event.getResourceId();
        long zoneId = event.getZoneId();
        if (EventTypes.EVENT_ISO_CREATE.equals(event.getType()) || EventTypes.EVENT_ISO_COPY.equals(event.getType())) {
            isoSize = event.getSize();
        }

        if (EventTypes.EVENT_ISO_CREATE.equals(event.getType()) || EventTypes.EVENT_ISO_COPY.equals(event.getType())) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("create iso with id : " + isoId + " for account: " + event.getAccountId());
            }
            List<UsageStorageVO> storageVOs = _usageStorageDao.listByIdAndZone(event.getAccountId(), isoId, StorageTypes.ISO, zoneId);
            if (storageVOs.size() > 0) {
                s_logger.warn("Usage entry for ISO: " + isoId + " assigned to account: " + event.getAccountId() + "already exists in zone " + zoneId);
                return;
            }
            Account acct = _accountDao.findByIdIncludingRemoved(event.getAccountId());
            UsageStorageVO storageVO =
                    new UsageStorageVO(isoId, zoneId, event.getAccountId(), acct.getDomainId(), StorageTypes.ISO, null, isoSize, isoSize, event.getCreateDate(), null);
            _usageStorageDao.persist(storageVO);
        } else if (EventTypes.EVENT_ISO_DELETE.equals(event.getType())) {
            List<UsageStorageVO> storageVOs;
            if (zoneId != -1L) {
                storageVOs = _usageStorageDao.listByIdAndZone(event.getAccountId(), isoId, StorageTypes.ISO, zoneId);
            } else {
                storageVOs = _usageStorageDao.listById(event.getAccountId(), isoId, StorageTypes.ISO);
            }

            if (storageVOs.size() > 1) {
                s_logger.warn("More that one usage entry for storage: " + isoId + " assigned to account: " + event.getAccountId() + "; marking them all as deleted...");
            }
            for (UsageStorageVO storageVO : storageVOs) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("deleting iso: " + storageVO.getId() + " from account: " + storageVO.getAccountId());
                }
                storageVO.setDeleted(event.getCreateDate()); // there really shouldn't be more than one
                _usageStorageDao.update(storageVO);
            }
        }
    }

};