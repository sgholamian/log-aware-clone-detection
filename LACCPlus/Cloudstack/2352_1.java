//,temp,BasicNetworkTopology.java,188,207,temp,AdvancedNetworkTopology.java,223,243
//,3
public class xxx {
    @Override
    public boolean applyLoadBalancingRules(final Network network, final List<LoadBalancingRule> rules, final VirtualRouter router)
            throws ResourceUnavailableException {

        if (rules == null || rules.isEmpty()) {
            s_logger.debug("No lb rules to be applied for network " + network.getId());
            return true;
        }

        s_logger.debug("APPLYING LOAD BALANCING RULES");

        final String typeString = "loadbalancing rules";
        final boolean isPodLevelException = false;
        final boolean failWhenDisconnect = false;
        final Long podId = null;

        final LoadBalancingRules loadBalancingRules = new LoadBalancingRules(network, rules);

        return applyRules(network, router, typeString, isPodLevelException, podId, failWhenDisconnect, new RuleApplierWrapper<RuleApplier>(loadBalancingRules));
    }

};