//,temp,NetworkHelperImpl.java,273,294,temp,InternalLoadBalancerVMManagerImpl.java,823,833
//,3
public class xxx {
    protected DomainRouterVO start(DomainRouterVO router, final User user, final Account caller, final Map<Param, Object> params, final DeploymentPlan planToDeploy)
            throws StorageUnavailableException, InsufficientCapacityException, ConcurrentOperationException, ResourceUnavailableException {
        s_logger.debug("Starting router " + router);
        try {
            _itMgr.advanceStart(router.getUuid(), params, planToDeploy, null);
        } catch (final OperationTimedoutException e) {
            throw new ResourceUnavailableException("Starting router " + router + " failed! " + e.toString(), DataCenter.class, router.getDataCenterId());
        }
        if (router.isStopPending()) {
            s_logger.info("Clear the stop pending flag of router " + router.getHostName() + " after start router successfully!");
            router.setStopPending(false);
            router = _routerDao.persist(router);
        }
        // We don't want the failure of VPN Connection affect the status of
        // router, so we try to make connection
        // only after router start successfully
        final Long vpcId = router.getVpcId();
        if (vpcId != null) {
            _s2sVpnMgr.reconnectDisconnectedVpnByVpc(vpcId);
        }
        return _routerDao.findById(router.getId());
    }

};