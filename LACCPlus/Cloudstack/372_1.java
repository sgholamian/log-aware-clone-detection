//,temp,NetworkACLManagerImpl.java,400,424,temp,VpcVirtualRouterElement.java,508,530
//,3
public class xxx {
    public boolean applyACLItemsToNetwork(final long networkId, final List<NetworkACLItemVO> rules) throws ResourceUnavailableException {
        final Network network = _networkDao.findById(networkId);
        boolean handled = false;
        boolean foundProvider = false;
        for (final NetworkACLServiceProvider element : _networkAclElements) {
            final Network.Provider provider = element.getProvider();
            final boolean isAclProvider = _networkModel.isProviderSupportServiceInNetwork(network.getId(), Service.NetworkACL, provider);
            if (!isAclProvider) {
                continue;
            }
            foundProvider = true;
            s_logger.debug("Applying NetworkACL for network: " + network.getId() + " with Network ACL service provider");
            handled = element.applyNetworkACLs(network, rules);
            if (handled) {
                // publish message on message bus, so that network elements implementing distributed routing
                // capability can act on the event
                _messageBus.publish(_name, "Network_ACL_Replaced", PublishScope.LOCAL, network);
                break;
            }
        }
        if (!foundProvider) {
            s_logger.debug("Unable to find NetworkACL service provider for network: " + network.getId());
        }
        return handled;
    }

};