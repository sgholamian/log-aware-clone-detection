//,temp,VirtualNetworkApplianceManagerImpl.java,1947,1967,temp,FirewallManagerImpl.java,696,726
//,3
public class xxx {
    @Override
    public boolean applyDefaultEgressFirewallRule(Long networkId, boolean defaultPolicy, boolean add) throws ResourceUnavailableException {

        s_logger.debug("applying default firewall egress rules ");

        NetworkVO network = _networkDao.findById(networkId);
        List<String> sourceCidr = new ArrayList<String>();
        List<String> destCidr = new ArrayList<String>();


        sourceCidr.add(network.getCidr());
        destCidr.add(NetUtils.ALL_IP4_CIDRS);

        FirewallRuleVO ruleVO =
            new FirewallRuleVO(null, null, null, null, "all", networkId, network.getAccountId(), network.getDomainId(), Purpose.Firewall, sourceCidr, destCidr, null, null, null,
                FirewallRule.TrafficType.Egress, FirewallRuleType.System);
        ruleVO.setState(add ? State.Add : State.Revoke);
        List<FirewallRuleVO> rules = new ArrayList<FirewallRuleVO>();
        rules.add(ruleVO);

        try {
            //this is not required to store in db because we don't to add this rule along with the normal rules
            if (!applyRules(rules, false, false)) {
                return  false;
            }
        } catch (ResourceUnavailableException ex) {
            s_logger.warn("Failed to apply default egress rules for guest network due to ", ex);
            return false;
        }
        return true;
    }

};