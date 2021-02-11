//,temp,NetScalerVMManagerImpl.java,206,214,temp,ElasticLoadBalancerManagerImpl.java,329,337
//,3
public class xxx {
    private DomainRouterVO stop(DomainRouterVO elbVm, boolean forced) throws ConcurrentOperationException, ResourceUnavailableException {
        s_logger.debug("Stopping ELB vm " + elbVm);
        try {
            _itMgr.advanceStop(elbVm.getUuid(), forced);
            return _routerDao.findById(elbVm.getId());
        } catch (OperationTimedoutException e) {
            throw new CloudRuntimeException("Unable to stop " + elbVm, e);
        }
    }

};