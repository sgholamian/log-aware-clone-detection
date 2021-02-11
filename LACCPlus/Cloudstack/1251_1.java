//,temp,ApplicationLoadBalancerManagerImpl.java,169,208,temp,LoadBalancingRulesManagerImpl.java,1730,1782
//,3
public class xxx {
    @DB
    protected ApplicationLoadBalancerRule persistLbRule(final ApplicationLoadBalancerRuleVO newRuleFinal) throws NetworkRuleConflictException {
        boolean success = true;
        ApplicationLoadBalancerRuleVO newRule = null;
        try {
            newRule = Transaction.execute(new TransactionCallbackWithException<ApplicationLoadBalancerRuleVO, NetworkRuleConflictException>() {
                @Override
                public ApplicationLoadBalancerRuleVO doInTransaction(TransactionStatus status) throws NetworkRuleConflictException {
                    //1) Persist the rule
                    ApplicationLoadBalancerRuleVO newRule = _lbDao.persist(newRuleFinal);

                    //2) Detect conflicts
                    detectLbRulesConflicts(newRule);
                    if (!_firewallDao.setStateToAdd(newRule)) {
                        throw new CloudRuntimeException("Unable to update the state to add for " + newRule);
                    }
                    s_logger.debug("Load balancer " + newRule.getId() + " for Ip address " + newRule.getSourceIp().addr() + ", source port " +
                        newRule.getSourcePortStart().intValue() + ", instance port " + newRule.getDefaultPortStart() + " is added successfully.");
                    CallContext.current().setEventDetails("Load balancer Id: " + newRule.getId());
                    Network ntwk = _networkModel.getNetwork(newRule.getNetworkId());
                    UsageEventUtils.publishUsageEvent(EventTypes.EVENT_LOAD_BALANCER_CREATE, newRule.getAccountId(), ntwk.getDataCenterId(), newRule.getId(), null,
                        LoadBalancingRule.class.getName(), newRule.getUuid());

                    return newRule;
                }
            });

            return newRule;
        } catch (Exception e) {
            success = false;
            if (e instanceof NetworkRuleConflictException) {
                throw (NetworkRuleConflictException)e;
            }
            throw new CloudRuntimeException("Unable to add lb rule for ip address " + newRuleFinal.getSourceIpAddressId(), e);
        } finally {
            if (!success && newRule != null) {
                _lbMgr.removeLBRule(newRule);
            }
        }
    }

};