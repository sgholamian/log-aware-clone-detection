//,temp,UsageManagerImpl.java,1783,1817,temp,UsageManagerImpl.java,1377,1412
//,3
public class xxx {
    private void createSecurityGroupEvent(UsageEventVO event) {

        long zoneId = -1L;

        long vmId = event.getResourceId();
        long sgId = event.getOfferingId();

        if (EventTypes.EVENT_SECURITY_GROUP_ASSIGN.equals(event.getType())) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Assigning : security group" + sgId + " to Vm: " + vmId + " for account: " + event.getAccountId());
            }
            zoneId = event.getZoneId();
            Account acct = _accountDao.findByIdIncludingRemoved(event.getAccountId());
            UsageSecurityGroupVO securityGroup = new UsageSecurityGroupVO(zoneId, event.getAccountId(), acct.getDomainId(), vmId, sgId, event.getCreateDate(), null);
            _usageSecurityGroupDao.persist(securityGroup);
        } else if (EventTypes.EVENT_SECURITY_GROUP_REMOVE.equals(event.getType())) {
            SearchCriteria<UsageSecurityGroupVO> sc = _usageSecurityGroupDao.createSearchCriteria();
            sc.addAnd("accountId", SearchCriteria.Op.EQ, event.getAccountId());
            sc.addAnd("vmInstanceId", SearchCriteria.Op.EQ, vmId);
            sc.addAnd("securityGroupId", SearchCriteria.Op.EQ, sgId);
            sc.addAnd("deleted", SearchCriteria.Op.NULL);
            List<UsageSecurityGroupVO> sgVOs = _usageSecurityGroupDao.search(sc, null);
            if (sgVOs.size() > 1) {
                s_logger.warn("More that one usage entry for security group: " + sgId + " for Vm: " + vmId + " assigned to account: " + event.getAccountId() +
                        "; marking them all as deleted...");
            }
            for (UsageSecurityGroupVO sgVO : sgVOs) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("deleting security group: " + sgVO.getSecurityGroupId() + " from Vm: " + sgVO.getVmInstanceId());
                }
                sgVO.setDeleted(event.getCreateDate()); // there really shouldn't be more than one
                _usageSecurityGroupDao.update(sgVO);
            }
        }
    }

};