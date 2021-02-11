//,temp,ApplicationLoadBalancerManagerImpl.java,169,208,temp,LoadBalancingRulesManagerImpl.java,1730,1782
//,3
public class xxx {
            @Override
            public LoadBalancerVO doInTransaction(TransactionStatus status) throws NetworkRuleConflictException {
                LoadBalancerVO newRule =
                    new LoadBalancerVO(xId, name, description, sourceIpId, srcPort, destPort, algorithm, networkId, ipAddr.getAllocatedToAccountId(),
                        ipAddr.getAllocatedInDomainId(), lbProtocol);

                if (forDisplay != null) {
                    newRule.setDisplay(forDisplay);
                }

                // verify rule is supported by Lb provider of the network
                Ip sourceIp = getSourceIp(newRule);
                LoadBalancingRule loadBalancing =
                    new LoadBalancingRule(newRule, new ArrayList<LbDestination>(), new ArrayList<LbStickinessPolicy>(), new ArrayList<LbHealthCheckPolicy>(), sourceIp,
                        null, lbProtocol);
                if (!validateLbRule(loadBalancing)) {
                    throw new InvalidParameterValueException("LB service provider cannot support this rule");
                }

                newRule = _lbDao.persist(newRule);

                //create rule for all CIDRs
                if (openFirewall) {
                    _firewallMgr.createRuleForAllCidrs(sourceIpId, caller.getCallingAccount(), srcPort, srcPort, protocol, null, null, newRule.getId(), networkId);
                }

                boolean success = true;

                try {
                    _firewallMgr.detectRulesConflict(newRule);
                    if (!_firewallDao.setStateToAdd(newRule)) {
                        throw new CloudRuntimeException("Unable to update the state to add for " + newRule);
                    }
                    s_logger.debug("Load balancer " + newRule.getId() + " for Ip address id=" + sourceIpId + ", public port " + srcPort + ", private port " + destPort +
                        " is added successfully.");
                    CallContext.current().setEventDetails("Load balancer Id: " + newRule.getId());
                    UsageEventUtils.publishUsageEvent(EventTypes.EVENT_LOAD_BALANCER_CREATE, ipAddr.getAllocatedToAccountId(), ipAddr.getDataCenterId(), newRule.getId(),
                        null, LoadBalancingRule.class.getName(), newRule.getUuid());

                    return newRule;
                } catch (Exception e) {
                    success = false;
                    if (e instanceof NetworkRuleConflictException) {
                        throw (NetworkRuleConflictException)e;
                    }
                    throw new CloudRuntimeException("Unable to add rule for ip address id=" + newRule.getSourceIpAddressId(), e);
                } finally {
                    if (!success && newRule != null) {
                        _firewallMgr.revokeRelatedFirewallRule(newRule.getId(), false);
                        removeLBRule(newRule);
                    }
                }
            }

};