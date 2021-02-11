//,temp,BasicNetworkTopology.java,229,246,temp,BasicNetworkTopology.java,188,207
//,2
public class xxx {
    @Override
    public boolean applyStaticNats(final Network network, final List<? extends StaticNat> rules, final VirtualRouter router) throws ResourceUnavailableException {
        if (rules == null || rules.isEmpty()) {
            s_logger.debug("No static nat rules to be applied for network " + network.getId());
            return true;
        }

        s_logger.debug("APPLYING STATIC NAT RULES");

        final String typeString = "static nat rules";
        final boolean isPodLevelException = false;
        final boolean failWhenDisconnect = false;
        final Long podId = null;

        final StaticNatRules natRules = new StaticNatRules(network, rules);

        return applyRules(network, router, typeString, isPodLevelException, podId, failWhenDisconnect, new RuleApplierWrapper<RuleApplier>(natRules));
    }

};