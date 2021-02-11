//,temp,RulesManagerImpl.java,1120,1162,temp,RulesManagerImpl.java,1074,1118
//,3
public class xxx {
    @Override
    public boolean revokeAllPFStaticNatRulesForNetwork(long networkId, long userId, Account caller) throws ResourceUnavailableException {
        List<FirewallRule> rules = new ArrayList<FirewallRule>();

        List<PortForwardingRuleVO> pfRules = _portForwardingDao.listByNetwork(networkId);
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Releasing " + pfRules.size() + " port forwarding rules for network id=" + networkId);
        }

        List<FirewallRuleVO> staticNatRules = _firewallDao.listByNetworkAndPurpose(networkId, Purpose.StaticNat);
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Releasing " + staticNatRules.size() + " static nat rules for network id=" + networkId);
        }

        // Mark all pf rules (Active and non-Active) to be revoked, but don't revoke it yet - pass apply=false
        for (PortForwardingRuleVO rule : pfRules) {
            revokePortForwardingRuleInternal(rule.getId(), caller, userId, false);
        }

        // Mark all static nat rules (Active and non-Active) to be revoked, but don't revoke it yet - pass apply=false
        for (FirewallRuleVO rule : staticNatRules) {
            revokeStaticNatRuleInternal(rule.getId(), caller, userId, false);
        }

        boolean success = true;
        // revoke all PF rules for the network
        success = success && applyPortForwardingRulesForNetwork(networkId, true, caller);
        success = success && applyPortForwardingRulesForNetwork(networkId,  _ipAddrMgr.RulesContinueOnError.value(), caller);

        // revoke all all static nat rules for the network
        success = success && applyStaticNatRulesForNetwork(networkId, true, caller);
        success = success && applyStaticNatRulesForNetwork(networkId,  _ipAddrMgr.RulesContinueOnError.value(), caller);

        // Now we check again in case more rules have been inserted.
        rules.addAll(_portForwardingDao.listByNetworkAndNotRevoked(networkId));
        rules.addAll(_firewallDao.listByNetworkAndPurposeAndNotRevoked(networkId, Purpose.StaticNat));

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Successfully released rules for network id=" + networkId + " and # of rules now = " + rules.size());
        }

        return success && rules.size() == 0;
    }

};