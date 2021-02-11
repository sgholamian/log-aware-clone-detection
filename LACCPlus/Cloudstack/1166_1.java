//,temp,F5ExternalLoadBalancerElement.java,123,145,temp,ElasticLoadBalancerElement.java,75,95
//,3
public class xxx {
    private boolean canHandle(Network config, List<LoadBalancingRule> rules) {
        if ((config.getGuestType() != Network.GuestType.Isolated && config.getGuestType() != Network.GuestType.Shared) || config.getTrafficType() != TrafficType.Guest) {

            s_logger.trace("Not handling network with Type  " + config.getGuestType() + " and traffic type " + config.getTrafficType());
            return false;
        }

        Map<Capability, String> lbCaps = this.getCapabilities().get(Service.Lb);
        if (!lbCaps.isEmpty()) {
            String schemeCaps = lbCaps.get(Capability.LbSchemes);
            if (schemeCaps != null && rules != null && !rules.isEmpty()) {
                for (LoadBalancingRule rule : rules) {
                    if (!schemeCaps.contains(rule.getScheme().toString())) {
                        s_logger.debug("Scheme " + rules.get(0).getScheme() + " is not supported by the provider " + this.getName());
                        return false;
                    }
                }
            }
        }

        return (_networkManager.isProviderForNetwork(getProvider(), config.getId()) && _ntwkSrvcDao.canProviderSupportServiceInNetwork(config.getId(), Service.Lb,
            Network.Provider.F5BigIp));
    }

};