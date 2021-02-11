//,temp,UsageManagerImpl.java,1671,1703,temp,UsageManagerImpl.java,1377,1412
//,3
public class xxx {
    private void createPortForwardingHelperEvent(UsageEventVO event) {

        long zoneId = -1L;

        long id = event.getResourceId();

        if (EventTypes.EVENT_NET_RULE_ADD.equals(event.getType())) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Creating port forwarding rule : " + id + " for account: " + event.getAccountId());
            }
            zoneId = event.getZoneId();
            Account acct = _accountDao.findByIdIncludingRemoved(event.getAccountId());
            UsagePortForwardingRuleVO pfVO = new UsagePortForwardingRuleVO(id, zoneId, event.getAccountId(), acct.getDomainId(), event.getCreateDate(), null);
            _usagePortForwardingRuleDao.persist(pfVO);
        } else if (EventTypes.EVENT_NET_RULE_DELETE.equals(event.getType())) {
            SearchCriteria<UsagePortForwardingRuleVO> sc = _usagePortForwardingRuleDao.createSearchCriteria();
            sc.addAnd("accountId", SearchCriteria.Op.EQ, event.getAccountId());
            sc.addAnd("id", SearchCriteria.Op.EQ, id);
            sc.addAnd("deleted", SearchCriteria.Op.NULL);
            List<UsagePortForwardingRuleVO> pfVOs = _usagePortForwardingRuleDao.search(sc, null);
            if (pfVOs.size() > 1) {
                s_logger.warn("More that one usage entry for port forwarding rule: " + id + " assigned to account: " + event.getAccountId() +
                        "; marking them all as deleted...");
            }
            for (UsagePortForwardingRuleVO pfVO : pfVOs) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("deleting port forwarding rule: " + pfVO.getId() + " from account: " + pfVO.getAccountId());
                }
                pfVO.setDeleted(event.getCreateDate()); // there really shouldn't be more than one
                _usagePortForwardingRuleDao.update(pfVO);
            }
        }
    }

};