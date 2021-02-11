//,temp,PrivateGatewayRules.java,118,149,temp,PrivateGatewayRules.java,51,93
//,3
public class xxx {
    @Override
    public boolean accept(final NetworkTopologyVisitor visitor, final VirtualRouter router) throws ResourceUnavailableException {
        _router = router;

        boolean result = false;
        try {
            final NetworkModel networkModel = visitor.getVirtualNetworkApplianceFactory().getNetworkModel();
            _network = networkModel.getNetwork(_privateGateway.getNetworkId());

            final NicProfileHelper nicProfileHelper = visitor.getVirtualNetworkApplianceFactory().getNicProfileHelper();
            final NicProfile requested = nicProfileHelper.createPrivateNicProfileForGateway(_privateGateway, _router);

            final NetworkHelper networkHelper = visitor.getVirtualNetworkApplianceFactory().getNetworkHelper();
            if (!networkHelper.checkRouterVersion(_router)) {
                s_logger.warn("Router requires upgrade. Unable to send command to router: " + _router.getId());
                return false;
            }
            final VirtualMachineManager itMgr = visitor.getVirtualNetworkApplianceFactory().getItMgr();
            _nicProfile = itMgr.addVmToNetwork(_router, _network, requested);

            // setup source nat
            if (_nicProfile != null) {
                _isAddOperation = true;
                // result = setupVpcPrivateNetwork(router, true, guestNic);
                result = visitor.visit(this);
            }
        } catch (final Exception ex) {
            s_logger.warn("Failed to create private gateway " + _privateGateway + " on router " + _router + " due to ", ex);
        } finally {
            if (!result) {
                s_logger.debug("Failed to setup gateway " + _privateGateway + " on router " + _router + " with the source nat. Will now remove the gateway.");
                _isAddOperation = false;
                final boolean isRemoved = destroyPrivateGateway(visitor);

                if (isRemoved) {
                    s_logger.debug("Removed the gateway " + _privateGateway + " from router " + _router + " as a part of cleanup");
                } else {
                    s_logger.warn("Failed to remove the gateway " + _privateGateway + " from router " + _router + " as a part of cleanup");
                }
            }
        }
        return result;
    }

};