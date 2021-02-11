//,temp,VirtualNetworkApplianceManagerImpl.java,1947,1967,temp,FirewallManagerImpl.java,696,726
//,3
public class xxx {
    private void createDefaultEgressFirewallRule(final List<FirewallRule> rules, final long networkId) {
        final NetworkVO network = _networkDao.findById(networkId);
        final NetworkOfferingVO offering = _networkOfferingDao.findById(network.getNetworkOfferingId());
        final Boolean defaultEgressPolicy = offering.isEgressDefaultPolicy();

        // The default on the router is set to Deny all. So, if the default configuration in the offering is set to true (Allow), we change the Egress here
        if (defaultEgressPolicy) {
            final List<String> sourceCidr = new ArrayList<String>();
            final List<String> destCidr = new ArrayList<String>();

            sourceCidr.add(network.getCidr());
            destCidr.add(NetUtils.ALL_IP4_CIDRS);

            final FirewallRule rule = new FirewallRuleVO(null, null, null, null, "all", networkId, network.getAccountId(), network.getDomainId(), Purpose.Firewall, sourceCidr,
                    destCidr, null, null, null, FirewallRule.TrafficType.Egress, FirewallRule.FirewallRuleType.System);

            rules.add(rule);
        } else {
            s_logger.debug("Egress policy for the Network " + networkId + " is already defined as Deny. So, no need to default the rule to Allow. ");
        }
    }

};