//,temp,UsageManagerImpl.java,1783,1817,temp,UsageManagerImpl.java,1750,1781
//,3
public class xxx {
    private void createVPNUserEvent(UsageEventVO event) {

        long zoneId = 0L;

        long userId = event.getResourceId();

        if (EventTypes.EVENT_VPN_USER_ADD.equals(event.getType())) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Creating VPN user: " + userId + " for account: " + event.getAccountId());
            }
            Account acct = _accountDao.findByIdIncludingRemoved(event.getAccountId());
            String userName = event.getResourceName();
            UsageVPNUserVO vpnUser = new UsageVPNUserVO(zoneId, event.getAccountId(), acct.getDomainId(), userId, userName, event.getCreateDate(), null);
            _usageVPNUserDao.persist(vpnUser);
        } else if (EventTypes.EVENT_VPN_USER_REMOVE.equals(event.getType())) {
            SearchCriteria<UsageVPNUserVO> sc = _usageVPNUserDao.createSearchCriteria();
            sc.addAnd("accountId", SearchCriteria.Op.EQ, event.getAccountId());
            sc.addAnd("userId", SearchCriteria.Op.EQ, userId);
            sc.addAnd("deleted", SearchCriteria.Op.NULL);
            List<UsageVPNUserVO> vuVOs = _usageVPNUserDao.search(sc, null);
            if (vuVOs.size() > 1) {
                s_logger.warn("More that one usage entry for vpn user: " + userId + " assigned to account: " + event.getAccountId() + "; marking them all as deleted...");
            }
            for (UsageVPNUserVO vuVO : vuVOs) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("deleting vpn user: " + vuVO.getUserId());
                }
                vuVO.setDeleted(event.getCreateDate()); // there really shouldn't be more than one
                _usageVPNUserDao.update(vuVO);
            }
        }
    }

};