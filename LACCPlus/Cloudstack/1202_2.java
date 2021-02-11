//,temp,NetworkHelperImpl.java,273,294,temp,InternalLoadBalancerVMManagerImpl.java,823,833
//,3
public class xxx {
    protected DomainRouterVO startInternalLbVm(DomainRouterVO internalLbVm, final Account caller, final long callerUserId, final Map<Param, Object> params)
            throws StorageUnavailableException, InsufficientCapacityException, ConcurrentOperationException, ResourceUnavailableException {
        s_logger.debug("Starting Internal LB VM " + internalLbVm);
        _itMgr.start(internalLbVm.getUuid(), params, null, null);
        if (internalLbVm.isStopPending()) {
            s_logger.info("Clear the stop pending flag of Internal LB VM " + internalLbVm.getHostName() + " after start router successfully!");
            internalLbVm.setStopPending(false);
            internalLbVm = _internalLbVmDao.persist(internalLbVm);
        }
        return _internalLbVmDao.findById(internalLbVm.getId());
    }

};