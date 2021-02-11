//,temp,VirtualRouterElement.java,179,206,temp,PaloAltoExternalFirewallElement.java,121,141
//,3
public class xxx {
    protected boolean canHandle(final Network network, final Service service) {
        final Long physicalNetworkId = _networkMdl.getPhysicalNetworkId(network);
        if (physicalNetworkId == null) {
            return false;
        }

        if (network.getVpcId() != null) {
            return false;
        }

        if (!_networkMdl.isProviderEnabledInPhysicalNetwork(physicalNetworkId, Network.Provider.VirtualRouter.getName())) {
            return false;
        }

        if (service == null) {
            if (!_networkMdl.isProviderForNetwork(getProvider(), network.getId())) {
                s_logger.trace("Element " + getProvider().getName() + " is not a provider for the network " + network);
                return false;
            }
        } else {
            if (!_networkMdl.isProviderSupportServiceInNetwork(network.getId(), service, getProvider())) {
                s_logger.trace("Element " + getProvider().getName() + " doesn't support service " + service.getName() + " in the network " + network);
                return false;
            }
        }

        return true;
    }

};