//,temp,UsageManagerImpl.java,1637,1669,temp,UsageManagerImpl.java,1377,1412
//,3
public class xxx {
    private void createIPHelperEvent(UsageEventVO event) {

        String ipAddress = event.getResourceName();

        if (EventTypes.EVENT_NET_IP_ASSIGN.equals(event.getType())) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("assigning ip address: " + ipAddress + " to account: " + event.getAccountId());
            }
            Account acct = _accountDao.findByIdIncludingRemoved(event.getAccountId());
            long zoneId = event.getZoneId();
            long id = event.getResourceId();
            long sourceNat = event.getSize();
            boolean isSourceNat = (sourceNat == 1) ? true : false;
            boolean isSystem = (event.getTemplateId() == null || event.getTemplateId() == 0) ? false : true;
            UsageIPAddressVO ipAddressVO =
                    new UsageIPAddressVO(id, event.getAccountId(), acct.getDomainId(), zoneId, ipAddress, isSourceNat, isSystem, event.getCreateDate(), null);
            _usageIPAddressDao.persist(ipAddressVO);
        } else if (EventTypes.EVENT_NET_IP_RELEASE.equals(event.getType())) {
            SearchCriteria<UsageIPAddressVO> sc = _usageIPAddressDao.createSearchCriteria();
            sc.addAnd("accountId", SearchCriteria.Op.EQ, event.getAccountId());
            sc.addAnd("address", SearchCriteria.Op.EQ, ipAddress);
            sc.addAnd("released", SearchCriteria.Op.NULL);
            List<UsageIPAddressVO> ipAddressVOs = _usageIPAddressDao.search(sc, null);
            if (ipAddressVOs.size() > 1) {
                s_logger.warn("More that one usage entry for ip address: " + ipAddress + " assigned to account: " + event.getAccountId() +
                        "; marking them all as released...");
            }
            for (UsageIPAddressVO ipAddressVO : ipAddressVOs) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("releasing ip address: " + ipAddressVO.getAddress() + " from account: " + ipAddressVO.getAccountId());
                }
                ipAddressVO.setReleased(event.getCreateDate()); // there really shouldn't be more than one
                _usageIPAddressDao.update(ipAddressVO);
            }
        }
    }

};