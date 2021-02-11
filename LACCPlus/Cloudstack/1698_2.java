//,temp,NetworkHelperImpl.java,240,257,temp,NetScalerVMManagerImpl.java,406,422
//,3
public class xxx {
    protected VirtualRouter stopNetScalerVm(final long vmId, final boolean forced, final Account caller, final long callerUserId) throws ResourceUnavailableException, ConcurrentOperationException {
        final DomainRouterVO netscalerVm = _routerDao.findById(vmId);
        s_logger.debug("Stopping NetScaler vm " + netscalerVm);

        if (netscalerVm == null || netscalerVm.getRole() != Role.NETSCALER_VM) {
            throw new InvalidParameterValueException("Can't find NetScaler vm by id specified");
        }

        _accountMgr.checkAccess(caller, null, true, netscalerVm);

        try {
            _itMgr.expunge(netscalerVm.getUuid());
            return _routerDao.findById(netscalerVm.getId());
        } catch (final Exception e) {
            throw new CloudRuntimeException("Unable to stop " + netscalerVm, e);
        }
    }

};