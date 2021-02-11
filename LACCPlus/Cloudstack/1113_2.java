//,temp,VpcVirtualRouterElement.java,312,338,temp,VpcVirtualRouterElement.java,284,310
//,3
public class xxx {
    @Override
    public boolean shutdown(final Network network, final ReservationContext context, final boolean cleanup) throws ConcurrentOperationException, ResourceUnavailableException {
        final Long vpcId = network.getVpcId();
        if (vpcId == null) {
            s_logger.debug("Network " + network + " doesn't belong to any vpc, so skipping unplug nic part");
            return true;
        }

        boolean success = true;
        final List<? extends VirtualRouter> routers = _routerDao.listByVpcId(vpcId);
        for (final VirtualRouter router : routers) {
            // 1) Check if router is already a part of the network
            if (!_networkMdl.isVmPartOfNetwork(router.getId(), network.getId())) {
                s_logger.debug("Router " + router + " is not a part the network " + network);
                continue;
            }
            // 2) Call unplugNics in the network service
            success = success && _vpcRouterMgr.removeVpcRouterFromGuestNetwork(router, network);
            if (!success) {
                s_logger.warn("Failed to unplug nic in network " + network + " for virtual router " + router);
            } else {
                s_logger.debug("Successfully unplugged nic in network " + network + " for virtual router " + router);
            }
        }

        return success;
    }

};