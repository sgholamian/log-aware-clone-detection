//,temp,UsageManagerImpl.java,1600,1635,temp,UsageManagerImpl.java,1502,1555
//,3
public class xxx {
    private void createTemplateHelperEvent(UsageEventVO event) {

        long templateId = -1L;
        long zoneId = -1L;
        long templateSize = -1L;

        templateId = event.getResourceId();
        zoneId = event.getZoneId();
        if (EventTypes.EVENT_TEMPLATE_CREATE.equals(event.getType()) || EventTypes.EVENT_TEMPLATE_COPY.equals(event.getType())) {
            templateSize = event.getSize();
            if (templateSize < 1) {
                s_logger.error("Incorrect size for template with Id " + templateId);
                return;
            }
            if (zoneId == -1L) {
                s_logger.error("Incorrect zoneId for template with Id " + templateId);
                return;
            }
        }

        if (EventTypes.EVENT_TEMPLATE_CREATE.equals(event.getType()) || EventTypes.EVENT_TEMPLATE_COPY.equals(event.getType())) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("create template with id : " + templateId + " for account: " + event.getAccountId());
            }
            List<UsageStorageVO> storageVOs = _usageStorageDao.listByIdAndZone(event.getAccountId(), templateId, StorageTypes.TEMPLATE, zoneId);
            if (storageVOs.size() > 0) {
                s_logger.warn("Usage entry for Template: " + templateId + " assigned to account: " + event.getAccountId() + "already exists in zone " + zoneId);
                return;
            }
            Account acct = _accountDao.findByIdIncludingRemoved(event.getAccountId());
            UsageStorageVO storageVO =
                    new UsageStorageVO(templateId, zoneId, event.getAccountId(), acct.getDomainId(), StorageTypes.TEMPLATE, event.getTemplateId(), templateSize,
                            event.getVirtualSize(), event.getCreateDate(), null);
            _usageStorageDao.persist(storageVO);
        } else if (EventTypes.EVENT_TEMPLATE_DELETE.equals(event.getType())) {
            List<UsageStorageVO> storageVOs;
            if (zoneId != -1L) {
                storageVOs = _usageStorageDao.listByIdAndZone(event.getAccountId(), templateId, StorageTypes.TEMPLATE, zoneId);
            } else {
                storageVOs = _usageStorageDao.listById(event.getAccountId(), templateId, StorageTypes.TEMPLATE);
            }
            if (storageVOs.size() > 1) {
                s_logger.warn("More that one usage entry for storage: " + templateId + " assigned to account: " + event.getAccountId() +
                        "; marking them all as deleted...");
            }
            for (UsageStorageVO storageVO : storageVOs) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("deleting template: " + storageVO.getId() + " from account: " + storageVO.getAccountId());
                }
                storageVO.setDeleted(event.getCreateDate()); // there really shouldn't be more than one
                _usageStorageDao.update(storageVO);
            }
        }
    }

};