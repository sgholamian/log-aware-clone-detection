//,temp,NetscalerElement.java,1443,1458,temp,ElasticLoadBalancerElement.java,75,95
//,3
public class xxx {
    private boolean canHandle(Network network, List<LoadBalancingRule> rules) {
        if (network.getGuestType() != Network.GuestType.Shared || network.getTrafficType() != TrafficType.Guest) {
            s_logger.debug("Not handling network with type  " + network.getGuestType() + " and traffic type " + network.getTrafficType());
            return false;
        }

        Map<Capability, String> lbCaps = this.getCapabilities().get(Service.Lb);
        if (!lbCaps.isEmpty()) {
            String schemeCaps = lbCaps.get(Capability.LbSchemes);
            if (schemeCaps != null) {
                for (LoadBalancingRule rule : rules) {
                    if (!schemeCaps.contains(rule.getScheme().toString())) {
                        s_logger.debug("Scheme " + rules.get(0).getScheme() + " is not supported by the provider " + this.getName());
                        return false;
                    }
                }
            }
        }

        return true;
    }

};