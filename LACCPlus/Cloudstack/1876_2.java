//,temp,RulesManagerImpl.java,940,968,temp,RulesManagerImpl.java,863,885
//,3
public class xxx {
    protected boolean applyPortForwardingRules(long ipId, boolean continueOnError, Account caller) {
        List<PortForwardingRuleVO> rules = _portForwardingDao.listForApplication(ipId);

        if (rules.size() == 0) {
            s_logger.debug("There are no port forwarding rules to apply for ip id=" + ipId);
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
            s_logger.warn("Failed to apply port forwarding rules for ip due to ", ex);
            return false;
        }

        return true;
    }

};