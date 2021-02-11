//,temp,OvsElement.java,562,578,temp,NetscalerElement.java,1443,1458
//,2
public class xxx {
    private boolean canHandleLbRules(final List<LoadBalancingRule> rules) {
        final Map<Capability, String> lbCaps = getCapabilities().get(Service.Lb);
        if (!lbCaps.isEmpty()) {
            final String schemeCaps = lbCaps.get(Capability.LbSchemes);
            if (schemeCaps != null) {
                for (final LoadBalancingRule rule : rules) {
                    if (!schemeCaps.contains(rule.getScheme().toString())) {
                        s_logger.debug("Scheme " + rules.get(0).getScheme()
                                + " is not supported by the provider "
                                + getName());
                        return false;
                    }
                }
            }
        }
        return true;
    }

};