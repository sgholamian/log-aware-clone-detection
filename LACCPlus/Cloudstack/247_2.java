//,temp,VirtualNetworkApplianceManagerImpl.java,2192,2202,temp,InternalLoadBalancerVMManagerImpl.java,548,557
//,3
public class xxx {
    protected VirtualRouter stopInternalLbVm(final DomainRouterVO internalLbVm, final boolean forced, final Account caller, final long callerUserId) throws ResourceUnavailableException,
    ConcurrentOperationException {
        s_logger.debug("Stopping internal lb vm " + internalLbVm);
        try {
            _itMgr.advanceStop(internalLbVm.getUuid(), forced);
            return _internalLbVmDao.findById(internalLbVm.getId());
        } catch (final OperationTimedoutException e) {
            throw new CloudRuntimeException("Unable to stop " + internalLbVm, e);
        }
    }

};