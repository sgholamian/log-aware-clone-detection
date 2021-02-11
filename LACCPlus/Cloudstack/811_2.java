//,temp,LoadBalanceRuleHandler.java,478,482,temp,NetScalerVMManagerImpl.java,206,214
//,3
public class xxx {
    protected VirtualRouter stopInternalLbVm(DomainRouterVO internalLbVm, boolean forced, Account caller, long callerUserId) throws ResourceUnavailableException, ConcurrentOperationException {
        s_logger.debug("Stopping internal lb vm " + internalLbVm);
        try {
            _itMgr.advanceStop(internalLbVm.getUuid(), forced);
            return _internalLbVmDao.findById(internalLbVm.getId());
        } catch (OperationTimedoutException e) {
            throw new CloudRuntimeException("Unable to stop " + internalLbVm, e);
        }
    }

};