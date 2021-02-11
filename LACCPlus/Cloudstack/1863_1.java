//,temp,PrivateGatewayRules.java,118,149,temp,PrivateGatewayRules.java,51,93
//,3
public class xxx {
    protected boolean destroyPrivateGateway(final NetworkTopologyVisitor visitor) throws ConcurrentOperationException, ResourceUnavailableException {

        final NetworkModel networkModel = visitor.getVirtualNetworkApplianceFactory().getNetworkModel();
        if (!networkModel.isVmPartOfNetwork(_router.getId(), _privateGateway.getNetworkId())) {
            s_logger.debug("Router doesn't have nic for gateway " + _privateGateway + " so no need to removed it");
            return true;
        }

        final Network privateNetwork = networkModel.getNetwork(_privateGateway.getNetworkId());

        s_logger.debug("Releasing private ip for gateway " + _privateGateway + " from " + _router);

        _nicProfile = networkModel.getNicProfile(_router, privateNetwork.getId(), null);
        boolean result = visitor.visit(this);
        if (!result) {
            s_logger.warn("Failed to release private ip for gateway " + _privateGateway + " on router " + _router);
            return false;
        }

        // revoke network acl on the private gateway.
        final NetworkACLManager networkACLMgr = visitor.getVirtualNetworkApplianceFactory().getNetworkACLMgr();
        if (!networkACLMgr.revokeACLItemsForPrivateGw(_privateGateway)) {
            s_logger.debug("Failed to delete network acl items on " + _privateGateway + " from router " + _router);
            return false;
        }

        s_logger.debug("Removing router " + _router + " from private network " + privateNetwork + " as a part of delete private gateway");
        final VirtualMachineManager itMgr = visitor.getVirtualNetworkApplianceFactory().getItMgr();
        result = result && itMgr.removeVmFromNetwork(_router, privateNetwork, null);
        s_logger.debug("Private gateawy " + _privateGateway + " is removed from router " + _router);
        return result;
    }

};