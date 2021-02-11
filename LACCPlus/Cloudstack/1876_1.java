//,temp,RulesManagerImpl.java,940,968,temp,RulesManagerImpl.java,863,885
//,3
public class xxx {
    @Override
    public boolean applyStaticNatRulesForNetwork(long networkId, boolean continueOnError, Account caller) {
        List<FirewallRuleVO> rules = _firewallDao.listByNetworkAndPurpose(networkId, Purpose.StaticNat);
        List<StaticNatRule> staticNatRules = new ArrayList<StaticNatRule>();

        if (rules.size() == 0) {
            s_logger.debug("There are no static nat rules to apply for network id=" + networkId);
            return true;
        }

        if (caller != null) {
            _accountMgr.checkAccess(caller, null, true, rules.toArray(new FirewallRule[rules.size()]));
        }

        for (FirewallRuleVO rule : rules) {
            staticNatRules.add(buildStaticNatRule(rule, false));
        }

        try {
            if (!_firewallMgr.applyRules(staticNatRules, continueOnError, true)) {
                return false;
            }
        } catch (ResourceUnavailableException ex) {
            s_logger.warn("Failed to apply static nat rules for network due to ", ex);
            return false;
        }

        return true;
    }

};