//,temp,VirtualNetworkApplianceManagerImpl.java,2192,2202,temp,NetScalerVMManagerImpl.java,206,214
//,3
public class xxx {
    @Override
    public DomainRouterVO stop(final VirtualRouter router, final boolean forced, final User user, final Account caller) throws ConcurrentOperationException,
    ResourceUnavailableException {
        s_logger.debug("Stopping router " + router);
        try {
            _itMgr.advanceStop(router.getUuid(), forced);
            return _routerDao.findById(router.getId());
        } catch (final OperationTimedoutException e) {
            throw new CloudRuntimeException("Unable to stop " + router, e);
        }
    }

};