//,temp,JuniperSRXExternalFirewallElement.java,127,148,temp,InternalLoadBalancerElement.java,127,155
//,3
public class xxx {
    private boolean canHandle(Network config, Scheme lbScheme) {
        //works in Advance zone only
        DataCenter dc = _entityMgr.findById(DataCenter.class, config.getDataCenterId());
        if (dc.getNetworkType() != NetworkType.Advanced) {
            s_logger.trace("Not hanling zone of network type " + dc.getNetworkType());
            return false;
        }
        if (config.getGuestType() != Network.GuestType.Isolated || config.getTrafficType() != TrafficType.Guest) {
            s_logger.trace("Not handling network with Type  " + config.getGuestType() + " and traffic type " + config.getTrafficType());
            return false;
        }

        Map<Capability, String> lbCaps = getCapabilities().get(Service.Lb);
        if (!lbCaps.isEmpty()) {
            String schemeCaps = lbCaps.get(Capability.LbSchemes);
            if (schemeCaps != null && lbScheme != null) {
                if (!schemeCaps.contains(lbScheme.toString())) {
                    s_logger.debug("Scheme " + lbScheme.toString() + " is not supported by the provider " + getName());
                    return false;
                }
            }
        }

        if (!_ntwkModel.isProviderSupportServiceInNetwork(config.getId(), Service.Lb, getProvider())) {
            s_logger.trace("Element " + getProvider().getName() + " doesn't support service " + Service.Lb + " in the network " + config);
            return false;
        }
        return true;
    }

};