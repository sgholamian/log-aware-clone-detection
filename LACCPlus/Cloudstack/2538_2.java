//,temp,PrivateGatewayRules.java,118,149,temp,VpcVirtualNetworkApplianceManagerImpl.java,558,587
//,3
public class xxx {
    @Override
    public boolean destroyPrivateGateway(final PrivateGateway gateway, final VirtualRouter router) throws ConcurrentOperationException, ResourceUnavailableException {
        boolean result = true;

        if (!_networkModel.isVmPartOfNetwork(router.getId(), gateway.getNetworkId())) {
            s_logger.debug("Router doesn't have nic for gateway " + gateway + " so no need to removed it");
            return result;
        }

        final Network privateNetwork = _networkModel.getNetwork(gateway.getNetworkId());
        final NicProfile nicProfile = _networkModel.getNicProfile(router, privateNetwork.getId(), null);

        s_logger.debug("Releasing private ip for gateway " + gateway + " from " + router);
        result = setupVpcPrivateNetwork(router, false, nicProfile);
        if (!result) {
            s_logger.warn("Failed to release private ip for gateway " + gateway + " on router " + router);
            return false;
        }

        // revoke network acl on the private gateway.
        if (!_networkACLMgr.revokeACLItemsForPrivateGw(gateway)) {
            s_logger.debug("Failed to delete network acl items on " + gateway + " from router " + router);
            return false;
        }

        s_logger.debug("Removing router " + router + " from private network " + privateNetwork + " as a part of delete private gateway");
        result = result && _itMgr.removeVmFromNetwork(router, privateNetwork, null);
        s_logger.debug("Private gateawy " + gateway + " is removed from router " + router);
        return result;
    }

};