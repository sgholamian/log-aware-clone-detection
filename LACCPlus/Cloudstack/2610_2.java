//,temp,VirtualRouterElement.java,645,666,temp,InternalLoadBalancerElement.java,231,251
//,3
public class xxx {
    @Override
    public boolean shutdown(Network network, ReservationContext context, boolean cleanup) throws ConcurrentOperationException, ResourceUnavailableException {
        List<? extends VirtualRouter> internalLbVms = _routerDao.listByNetworkAndRole(network.getId(), Role.INTERNAL_LB_VM);
        if (internalLbVms == null || internalLbVms.isEmpty()) {
            return true;
        }
        boolean result = true;
        for (VirtualRouter internalLbVm : internalLbVms) {
            result = result && _internalLbMgr.destroyInternalLbVm(internalLbVm.getId(), context.getAccount(), context.getCaller().getId());
            if (cleanup) {
                if (!result) {
                    s_logger.warn("Failed to stop internal lb element " + internalLbVm + ", but would try to process clean up anyway.");
                }
                result = (_internalLbMgr.destroyInternalLbVm(internalLbVm.getId(), context.getAccount(), context.getCaller().getId()));
                if (!result) {
                    s_logger.warn("Failed to clean up internal lb element " + internalLbVm);
                }
            }
        }
        return result;
    }

};