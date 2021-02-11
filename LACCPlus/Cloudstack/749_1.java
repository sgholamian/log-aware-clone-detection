//,temp,BasicNetworkTopology.java,209,227,temp,BasicNetworkTopology.java,188,207
//,2
public class xxx {
    @Override
    public boolean applyFirewallRules(final Network network, final List<? extends FirewallRule> rules, final VirtualRouter router)
            throws ResourceUnavailableException {
        if (rules == null || rules.isEmpty()) {
            s_logger.debug("No firewall rules to be applied for network " + network.getId());
            return true;
        }

        s_logger.debug("APPLYING FIREWALL RULES");

        final String typeString = "firewall rules";
        final boolean isPodLevelException = false;
        final boolean failWhenDisconnect = false;
        final Long podId = null;

        final FirewallRules firewallRules = new FirewallRules(network, rules);

        return applyRules(network, router, typeString, isPodLevelException, podId, failWhenDisconnect, new RuleApplierWrapper<RuleApplier>(firewallRules));
    }

};