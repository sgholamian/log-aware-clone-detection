//,temp,RulesManagerImpl.java,940,968,temp,RulesManagerImpl.java,887,914
//,3
public class xxx {
    protected boolean applyStaticNatRulesForIp(long sourceIpId, boolean continueOnError, Account caller, boolean forRevoke) {
        List<? extends FirewallRule> rules = _firewallDao.listByIpAndPurpose(sourceIpId, Purpose.StaticNat);
        List<StaticNatRule> staticNatRules = new ArrayList<StaticNatRule>();

        if (rules.size() == 0) {
            s_logger.debug("There are no static nat rules to apply for ip id=" + sourceIpId);
            return true;
        }

        for (FirewallRule rule : rules) {
            staticNatRules.add(buildStaticNatRule(rule, forRevoke));
        }

        if (caller != null) {
            _accountMgr.checkAccess(caller, null, true, staticNatRules.toArray(new StaticNatRule[staticNatRules.size()]));
        }

        try {
            if (!_firewallMgr.applyRules(staticNatRules, continueOnError, true)) {
                return false;
            }
        } catch (ResourceUnavailableException ex) {
            s_logger.warn("Failed to apply static nat rules for ip due to ", ex);
            return false;
        }

        return true;
    }

};