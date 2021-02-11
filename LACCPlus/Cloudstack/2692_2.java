//,temp,LoadBalancingRulesManagerImpl.java,785,851,temp,LoadBalancingRulesManagerImpl.java,733,783
//,3
public class xxx {
    @Override
    @ActionEvent(eventType = EventTypes.EVENT_LB_STICKINESSPOLICY_DELETE, eventDescription = "revoking LB Stickiness policy ", async = true)
    public boolean deleteLBStickinessPolicy(long stickinessPolicyId, boolean apply) {
        boolean success = true;

        CallContext caller = CallContext.current();
        LBStickinessPolicyVO stickinessPolicy = _lb2stickinesspoliciesDao.findById(stickinessPolicyId);

        if (stickinessPolicy == null) {
            throw new InvalidParameterException("Invalid Stickiness policy id value: " + stickinessPolicyId);
        }
        LoadBalancerVO loadBalancer = _lbDao.findById(Long.valueOf(stickinessPolicy.getLoadBalancerId()));
        if (loadBalancer == null) {
            throw new InvalidParameterException("Invalid Load balancer : " + stickinessPolicy.getLoadBalancerId() + " for Stickiness policy id: " + stickinessPolicyId);
        }
        long loadBalancerId = loadBalancer.getId();
        FirewallRule.State backupState = loadBalancer.getState();
        _accountMgr.checkAccess(caller.getCallingAccount(), null, true, loadBalancer);

        if (apply) {
            if (loadBalancer.getState() == FirewallRule.State.Active) {
                loadBalancer.setState(FirewallRule.State.Add);
                _lbDao.persist(loadBalancer);
            }

            boolean backupStickyState = stickinessPolicy.isRevoke();
            stickinessPolicy.setRevoke(true);
            _lb2stickinesspoliciesDao.persist(stickinessPolicy);
            s_logger.debug("Set load balancer rule for revoke: rule id " + loadBalancerId + ", stickinesspolicyID " + stickinessPolicyId);

            try {
                if (!applyLoadBalancerConfig(loadBalancerId)) {
                    s_logger.warn("Failed to remove load balancer rule id " + loadBalancerId + " for stickinesspolicyID " + stickinessPolicyId);
                    throw new CloudRuntimeException("Failed to remove load balancer rule id " + loadBalancerId + " for stickinesspolicyID " + stickinessPolicyId);
                }
            } catch (ResourceUnavailableException e) {
                if (isRollBackAllowedForProvider(loadBalancer)) {
                    stickinessPolicy.setRevoke(backupStickyState);
                    _lb2stickinesspoliciesDao.persist(stickinessPolicy);
                    loadBalancer.setState(backupState);
                    _lbDao.persist(loadBalancer);
                    s_logger.debug("LB Rollback rule id: " + loadBalancer.getId() + "  while deleting sticky policy: " + stickinessPolicyId);
                }
                s_logger.warn("Unable to apply the load balancer config because resource is unavaliable.", e);
                success = false;
            }
        } else {
            _lb2stickinesspoliciesDao.expunge(stickinessPolicyId);
        }
        return success;
    }

};