//,temp,VirtualMachineManagerImpl.java,3569,3614,temp,VirtualMachineManagerImpl.java,3453,3512
//,3
public class xxx {
    private NicProfile orchestrateAddVmToNetwork(final VirtualMachine vm, final Network network, final NicProfile requested) throws ConcurrentOperationException, ResourceUnavailableException,
    InsufficientCapacityException {
        final CallContext cctx = CallContext.current();

        s_logger.debug("Adding vm " + vm + " to network " + network + "; requested nic profile " + requested);
        final VMInstanceVO vmVO = _vmDao.findById(vm.getId());
        final ReservationContext context = new ReservationContextImpl(null, null, cctx.getCallingUser(), cctx.getCallingAccount());

        final VirtualMachineProfileImpl vmProfile = new VirtualMachineProfileImpl(vmVO, null, null, null, null);

        final DataCenter dc = _entityMgr.findById(DataCenter.class, network.getDataCenterId());
        final Host host = _hostDao.findById(vm.getHostId());
        final DeployDestination dest = new DeployDestination(dc, null, null, host);

        //check vm state
        if (vm.getState() == State.Running) {
            //1) allocate and prepare nic
            final NicProfile nic = _networkMgr.createNicForVm(network, requested, context, vmProfile, true);

            //2) Convert vmProfile to vmTO
            final HypervisorGuru hvGuru = _hvGuruMgr.getGuru(vmProfile.getVirtualMachine().getHypervisorType());
            final VirtualMachineTO vmTO = hvGuru.implement(vmProfile);

            //3) Convert nicProfile to NicTO
            final NicTO nicTO = toNicTO(nic, vmProfile.getVirtualMachine().getHypervisorType());

            //4) plug the nic to the vm
            s_logger.debug("Plugging nic for vm " + vm + " in network " + network);

            boolean result = false;
            try {
                result = plugNic(network, nicTO, vmTO, context, dest);
                if (result) {
                    s_logger.debug("Nic is plugged successfully for vm " + vm + " in network " + network + ". Vm  is a part of network now");
                    final long isDefault = nic.isDefaultNic() ? 1 : 0;
                    // insert nic's Id into DB as resource_name
                    if(VirtualMachine.Type.User.equals(vmVO.getType())) {
                        //Log usage event for user Vms only
                        UsageEventUtils.publishUsageEvent(EventTypes.EVENT_NETWORK_OFFERING_ASSIGN, vmVO.getAccountId(), vmVO.getDataCenterId(), vmVO.getId(),
                                Long.toString(nic.getId()), network.getNetworkOfferingId(), null, isDefault, VirtualMachine.class.getName(), vmVO.getUuid(), vm.isDisplay());
                    }
                    return nic;
                } else {
                    s_logger.warn("Failed to plug nic to the vm " + vm + " in network " + network);
                    return null;
                }
            } finally {
                if (!result) {
                    s_logger.debug("Removing nic " + nic + " from vm " + vmProfile.getVirtualMachine() + " as nic plug failed on the backend");
                    _networkMgr.removeNic(vmProfile, _nicsDao.findById(nic.getId()));
                }
            }
        } else if (vm.getState() == State.Stopped) {
            //1) allocate nic
            return _networkMgr.createNicForVm(network, requested, context, vmProfile, false);
        } else {
            s_logger.warn("Unable to add vm " + vm + " to network  " + network);
            throw new ResourceUnavailableException("Unable to add vm " + vm + " to network, is not in the right state", DataCenter.class, vm.getDataCenterId());
        }
    }

};