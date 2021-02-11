//,temp,InternalLoadBalancerVMManagerImpl.java,517,533,temp,NetScalerVMManagerImpl.java,429,444
//,3
public class xxx {
    @Override
    public VirtualRouter stopNetScalerVm(Long vmId, boolean forced, Account caller, long callingUserId) {
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