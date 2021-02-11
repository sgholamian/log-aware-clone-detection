//,temp,VpcVirtualRouterElement.java,312,338,temp,VpcVirtualRouterElement.java,284,310
//,3
public class xxx {
    @Override
    public boolean destroy(final Network config, final ReservationContext context) throws ConcurrentOperationException, ResourceUnavailableException {
        final Long vpcId = config.getVpcId();
        if (vpcId == null) {
            s_logger.debug("Network " + config + " doesn't belong to any vpc, so skipping unplug nic part");
            return true;
        }

        boolean success = true;
        final List<? extends VirtualRouter> routers = _routerDao.listByVpcId(vpcId);
        for (final VirtualRouter router : routers) {
            // 1) Check if router is already a part of the network
            if (!_networkMdl.isVmPartOfNetwork(router.getId(), config.getId())) {
                s_logger.debug("Router " + router + " is not a part the network " + config);
                continue;
            }
            // 2) Call unplugNics in the network service
            success = success && _vpcRouterMgr.removeVpcRouterFromGuestNetwork(router, config);
            if (!success) {
                s_logger.warn("Failed to unplug nic in network " + config + " for virtual router " + router);
            } else {
                s_logger.debug("Successfully unplugged nic in network " + config + " for virtual router " + router);
            }
        }

        return success;
    }

};