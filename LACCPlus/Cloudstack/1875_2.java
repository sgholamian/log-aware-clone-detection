//,temp,RulesManagerImpl.java,940,968,temp,RulesManagerImpl.java,916,938
//,3
public class xxx {
    @Override
    public boolean applyPortForwardingRulesForNetwork(long networkId, boolean continueOnError, Account caller) {
        List<PortForwardingRuleVO> rules = listByNetworkId(networkId);
        if (rules.size() == 0) {
            s_logger.debug("There are no port forwarding rules to apply for network id=" + networkId);
            return true;
        }

        if (caller != null) {
            _accountMgr.checkAccess(caller, null, true, rules.toArray(new PortForwardingRuleVO[rules.size()]));
        }

        try {
            if (!_firewallMgr.applyRules(rules, continueOnError, true)) {
                return false;
            }
        } catch (ResourceUnavailableException ex) {
            s_logger.warn("Failed to apply port forwarding rules for network due to ", ex);
            return false;
        }

        return true;
    }

};