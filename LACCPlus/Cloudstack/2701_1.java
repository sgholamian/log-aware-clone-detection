//,temp,UsageManagerImpl.java,1834,1869,temp,UsageManagerImpl.java,1783,1817
//,3
public class xxx {
    private void createVmSnapshotOnPrimaryEvent(UsageEventVO event) {
        Long vmId = event.getResourceId();
        String name = event.getResourceName();
        if (EventTypes.EVENT_VM_SNAPSHOT_ON_PRIMARY.equals(event.getType())) {
            Long zoneId = event.getZoneId();
            Long accountId = event.getAccountId();
            long physicalsize = (event.getSize() == null) ? 0 : event.getSize();
            long virtualsize = (event.getVirtualSize() == null) ? 0 : event.getVirtualSize();
            Date created = event.getCreateDate();
            Account acct = _accountDao.findByIdIncludingRemoved(event.getAccountId());
            Long domainId = acct.getDomainId();
            UsageSnapshotOnPrimaryVO vsVO = new UsageSnapshotOnPrimaryVO(vmId, zoneId, accountId, domainId, vmId, name, 0, virtualsize, physicalsize, created, null);
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("createSnapshotOnPrimaryEvent UsageSnapshotOnPrimaryVO " + vsVO);
            }
            _usageSnapshotOnPrimaryDao.persist(vsVO);
        } else if (EventTypes.EVENT_VM_SNAPSHOT_OFF_PRIMARY.equals(event.getType())) {
            QueryBuilder<UsageSnapshotOnPrimaryVO> sc = QueryBuilder.create(UsageSnapshotOnPrimaryVO.class);
            sc.and(sc.entity().getAccountId(), SearchCriteria.Op.EQ, event.getAccountId());
            sc.and(sc.entity().getId(), SearchCriteria.Op.EQ, vmId);
            sc.and(sc.entity().getName(), SearchCriteria.Op.EQ, name);
            sc.and(sc.entity().getDeleted(), SearchCriteria.Op.NULL);
            List<UsageSnapshotOnPrimaryVO> vmsnaps = sc.list();
            if (vmsnaps.size() > 1) {
                s_logger.warn("More that one usage entry for vm snapshot: " + name + " for vm id:" + vmId + " assigned to account: " + event.getAccountId()
                        + "; marking them all as deleted...");
            }
            for (UsageSnapshotOnPrimaryVO vmsnap : vmsnaps) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("deleting vm snapshot name: " + vmsnap.getName() + " from account: " + vmsnap.getAccountId());
                }
                vmsnap.setDeleted(event.getCreateDate()); // there really shouldn't be more than one
                _usageSnapshotOnPrimaryDao.updateDeleted(vmsnap);
            }
        }
    }

};