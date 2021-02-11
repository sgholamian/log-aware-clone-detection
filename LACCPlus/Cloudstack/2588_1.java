//,temp,FirewallManagerImpl.java,550,588,temp,VpcManagerImpl.java,1919,1951
//,3
public class xxx {
    @Override
    public boolean applyRules(List<? extends FirewallRule> rules, boolean continueOnError, boolean updateRulesInDB) throws ResourceUnavailableException {
        boolean success = true;
        if (rules == null || rules.size() == 0) {
            s_logger.debug("There are no rules to forward to the network elements");
            return true;
        }
        Purpose purpose = rules.get(0).getPurpose();
        if (!_ipAddrMgr.applyRules(rules, purpose, this, continueOnError)) {
            s_logger.warn("Rules are not completely applied");
            return false;
        } else {
            if (updateRulesInDB) {
                for (FirewallRule rule : rules) {
                    if (rule.getState() == FirewallRule.State.Revoke) {
                        FirewallRuleVO relatedRule = _firewallDao.findByRelatedId(rule.getId());
                        if (relatedRule != null) {
                            s_logger.warn("Can't remove the firewall rule id=" + rule.getId() + " as it has related firewall rule id=" + relatedRule.getId() +
                                "; leaving it in Revoke state");
                            success = false;
                        } else {
                            removeRule(rule);
                            if (rule.getSourceIpAddressId() != null) {
                                //if the rule is the last one for the ip address assigned to VPC, unassign it from the network
                                IpAddress ip = _ipAddressDao.findById(rule.getSourceIpAddressId());
                                _vpcMgr.unassignIPFromVpcNetwork(ip.getId(), rule.getNetworkId());
                            }
                        }
                    } else if (rule.getState() == FirewallRule.State.Add) {
                        FirewallRuleVO ruleVO = _firewallDao.findById(rule.getId());
                        ruleVO.setState(FirewallRule.State.Active);
                        _firewallDao.update(ruleVO.getId(), ruleVO);
                    }
                }
            }
        }

        return success;
    }

};