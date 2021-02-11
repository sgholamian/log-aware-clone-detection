//,temp,VirtualRouterElement.java,645,666,temp,InternalLoadBalancerElement.java,231,251
//,3
public class xxx {
    @Override
    public boolean shutdown(final Network network, final ReservationContext context, final boolean cleanup) throws ConcurrentOperationException, ResourceUnavailableException {
        final List<DomainRouterVO> routers = getRouters(network);
        if (routers == null || routers.isEmpty()) {
            return true;
        }
        boolean stopResult = true;
        boolean destroyResult = true;
        for (final DomainRouterVO router : routers) {
            stopResult = stopResult && _routerMgr.stop(router, false, context.getCaller(), context.getAccount()) != null;
            if (!stopResult) {
                s_logger.warn("Failed to stop virtual router element " + router + ", but would try to process clean up anyway.");
            }
            if (cleanup) {
                destroyResult = destroyResult && _routerMgr.destroyRouter(router.getId(), context.getAccount(), context.getCaller().getId()) != null;
                if (!destroyResult) {
                    s_logger.warn("Failed to clean up virtual router element " + router);
                }
            }
        }
        return stopResult & destroyResult;
    }

};