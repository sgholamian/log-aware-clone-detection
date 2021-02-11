//,temp,RulesManagerImpl.java,1120,1162,temp,RulesManagerImpl.java,1074,1118
//,3
public class xxx {
    @Override
    public boolean revokeAllPFAndStaticNatRulesForIp(long ipId, long userId, Account caller) throws ResourceUnavailableException {
        List<FirewallRule> rules = new ArrayList<FirewallRule>();

        List<PortForwardingRuleVO> pfRules = _portForwardingDao.listByIpAndNotRevoked(ipId);
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Releasing " + pfRules.size() + " port forwarding rules for ip id=" + ipId);
        }

        for (PortForwardingRuleVO rule : pfRules) {
            // Mark all PF rules as Revoke, but don't revoke them yet
            revokePortForwardingRuleInternal(rule.getId(), caller, userId, false);
        }

        List<FirewallRuleVO> staticNatRules = _firewallDao.listByIpAndPurposeAndNotRevoked(ipId, Purpose.StaticNat);
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Releasing " + staticNatRules.size() + " static nat rules for ip id=" + ipId);
        }

        for (FirewallRuleVO rule : staticNatRules) {
            // Mark all static nat rules as Revoke, but don't revoke them yet
            revokeStaticNatRuleInternal(rule.getId(), caller, userId, false);
        }

        boolean success = true;

        // revoke all port forwarding rules
        success = success && applyPortForwardingRules(ipId,  _ipAddrMgr.RulesContinueOnError.value(), caller);

        // revoke all all static nat rules
        success = success && applyStaticNatRulesForIp(ipId,  _ipAddrMgr.RulesContinueOnError.value(), caller, true);

        // revoke static nat for the ip address
        success = success && applyStaticNatForIp(ipId, false, caller, true);

        // Now we check again in case more rules have been inserted.
        rules.addAll(_portForwardingDao.listByIpAndNotRevoked(ipId));
        rules.addAll(_firewallDao.listByIpAndPurposeAndNotRevoked(ipId, Purpose.StaticNat));

        if (s_logger.isDebugEnabled() && success) {
            s_logger.debug("Successfully released rules for ip id=" + ipId + " and # of rules now = " + rules.size());
        }

        return (rules.size() == 0 && success);
    }

};