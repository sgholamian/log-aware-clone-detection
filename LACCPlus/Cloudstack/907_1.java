//,temp,InternalLoadBalancerVMManagerImpl.java,517,533,temp,NetScalerVMManagerImpl.java,406,422
//,3
public class xxx {
    @Override
    public boolean destroyInternalLbVm(final long vmId, final Account caller, final Long callerUserId) throws ResourceUnavailableException, ConcurrentOperationException {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Attempting to destroy Internal LB vm " + vmId);
        }

        final DomainRouterVO internalLbVm = _internalLbVmDao.findById(vmId);
        if (internalLbVm == null) {
            return true;
        }

        _accountMgr.checkAccess(caller, null, true, internalLbVm);

        _itMgr.expunge(internalLbVm.getUuid());
        _internalLbVmDao.remove(internalLbVm.getId());
        return true;
    }

};