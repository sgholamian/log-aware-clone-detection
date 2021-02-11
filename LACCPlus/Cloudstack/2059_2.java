//,temp,UsageManagerImpl.java,1750,1781,temp,UsageManagerImpl.java,1637,1669
//,3
public class xxx {
    private void createLoadBalancerHelperEvent(UsageEventVO event) {

        long zoneId = -1L;

        long id = event.getResourceId();

        if (EventTypes.EVENT_LOAD_BALANCER_CREATE.equals(event.getType())) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Creating load balancer : " + id + " for account: " + event.getAccountId());
            }
            zoneId = event.getZoneId();
            Account acct = _accountDao.findByIdIncludingRemoved(event.getAccountId());
            UsageLoadBalancerPolicyVO lbVO = new UsageLoadBalancerPolicyVO(id, zoneId, event.getAccountId(), acct.getDomainId(), event.getCreateDate(), null);
            _usageLoadBalancerPolicyDao.persist(lbVO);
        } else if (EventTypes.EVENT_LOAD_BALANCER_DELETE.equals(event.getType())) {
            SearchCriteria<UsageLoadBalancerPolicyVO> sc = _usageLoadBalancerPolicyDao.createSearchCriteria();
            sc.addAnd("accountId", SearchCriteria.Op.EQ, event.getAccountId());
            sc.addAnd("id", SearchCriteria.Op.EQ, id);
            sc.addAnd("deleted", SearchCriteria.Op.NULL);
            List<UsageLoadBalancerPolicyVO> lbVOs = _usageLoadBalancerPolicyDao.search(sc, null);
            if (lbVOs.size() > 1) {
                s_logger.warn("More that one usage entry for load balancer policy: " + id + " assigned to account: " + event.getAccountId() +
                        "; marking them all as deleted...");
            }
            for (UsageLoadBalancerPolicyVO lbVO : lbVOs) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("deleting load balancer policy: " + lbVO.getId() + " from account: " + lbVO.getAccountId());
                }
                lbVO.setDeleted(event.getCreateDate()); // there really shouldn't be more than one
                _usageLoadBalancerPolicyDao.update(lbVO);
            }
        }
    }

};