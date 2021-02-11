//,temp,NetscalerElement.java,1443,1458,temp,ElasticLoadBalancerElement.java,75,95
//,3
public class xxx {
    private boolean canHandleLbRules(List<LoadBalancingRule> rules) {
        Map<Capability, String> lbCaps = getCapabilities().get(Service.Lb);
        if (!lbCaps.isEmpty()) {
            String schemeCaps = lbCaps.get(Capability.LbSchemes);
            if (schemeCaps != null) {
                for (LoadBalancingRule rule : rules) {
                    if (!schemeCaps.contains(rule.getScheme().toString())) {
                        s_logger.debug("Scheme " + rules.get(0).getScheme() + " is not supported by the provider "
                                + getName());
                        return false;
                    }
                }
            }
        }
        return true;
    }

};