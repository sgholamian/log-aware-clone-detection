//,temp,LoadBalancingRulesManagerImpl.java,785,851,temp,LoadBalancingRulesManagerImpl.java,649,703
//,3
public class xxx {
    @Override
    @DB
    @ActionEvent(eventType = EventTypes.EVENT_LB_STICKINESSPOLICY_CREATE, eventDescription = "Apply Stickinesspolicy to load balancer ", async = true)
    public boolean applyLBStickinessPolicy(CreateLBStickinessPolicyCmd cmd) {
        boolean success = true;
        FirewallRule.State backupState = null;
        long oldStickinessPolicyId = 0;

        LoadBalancerVO loadBalancer = _lbDao.findById(cmd.getLbRuleId());
        if (loadBalancer == null) {
            throw new InvalidParameterException("Invalid Load balancer Id:" + cmd.getLbRuleId());
        }
        List<LBStickinessPolicyVO> stickinessPolicies = _lb2stickinesspoliciesDao.listByLoadBalancerId(cmd.getLbRuleId(), false);
        for (LBStickinessPolicyVO stickinessPolicy : stickinessPolicies) {
            if (stickinessPolicy.getId() == cmd.getEntityId()) {
                backupState = loadBalancer.getState();
                loadBalancer.setState(FirewallRule.State.Add);
                _lbDao.persist(loadBalancer);
            } else {
                oldStickinessPolicyId = stickinessPolicy.getId();
                stickinessPolicy.setRevoke(true);
                _lb2stickinesspoliciesDao.persist(stickinessPolicy);
            }
        }
        try {
            applyLoadBalancerConfig(cmd.getLbRuleId());
        } catch (ResourceUnavailableException e) {
            s_logger.warn("Unable to apply Stickiness policy to the lb rule: " + cmd.getLbRuleId() + " because resource is unavaliable:", e);
            if (isRollBackAllowedForProvider(loadBalancer)) {
                loadBalancer.setState(backupState);
                _lbDao.persist(loadBalancer);
                deleteLBStickinessPolicy(cmd.getEntityId(), false);
                s_logger.debug("LB Rollback rule id: " + loadBalancer.getId() + " lb state rolback while creating sticky policy");
            } else {
                deleteLBStickinessPolicy(cmd.getEntityId(), false);
                if (oldStickinessPolicyId != 0) {
                    LBStickinessPolicyVO stickinessPolicy = _lb2stickinesspoliciesDao.findById(oldStickinessPolicyId);
                    stickinessPolicy.setRevoke(false);
                    _lb2stickinesspoliciesDao.persist(stickinessPolicy);
                    try {
                        if (backupState.equals(FirewallRule.State.Active))
                            applyLoadBalancerConfig(cmd.getLbRuleId());
                    } catch (ResourceUnavailableException e1) {
                        s_logger.info("[ignored] applying load balancer config.", e1);
                    } finally {
                        loadBalancer.setState(backupState);
                        _lbDao.persist(loadBalancer);
                    }
                }
            }
            success = false;
        }

        return success;
    }

};