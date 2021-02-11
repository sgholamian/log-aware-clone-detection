//,temp,VirtualMachineManagerImpl.java,3623,3708,temp,VirtualMachineManagerImpl.java,3569,3614
//,3
public class xxx {
    private boolean orchestrateRemoveNicFromVm(final VirtualMachine vm, final Nic nic) throws ConcurrentOperationException, ResourceUnavailableException {
        final CallContext cctx = CallContext.current();
        final VMInstanceVO vmVO = _vmDao.findById(vm.getId());
        final NetworkVO network = _networkDao.findById(nic.getNetworkId());
        final ReservationContext context = new ReservationContextImpl(null, null, cctx.getCallingUser(), cctx.getCallingAccount());

        final VirtualMachineProfileImpl vmProfile = new VirtualMachineProfileImpl(vmVO, null, null, null, null);

        final DataCenter dc = _entityMgr.findById(DataCenter.class, network.getDataCenterId());
        final Host host = _hostDao.findById(vm.getHostId());
        final DeployDestination dest = new DeployDestination(dc, null, null, host);
        final HypervisorGuru hvGuru = _hvGuruMgr.getGuru(vmProfile.getVirtualMachine().getHypervisorType());
        final VirtualMachineTO vmTO = hvGuru.implement(vmProfile);

        final NicProfile nicProfile =
                new NicProfile(nic, network, nic.getBroadcastUri(), nic.getIsolationUri(), _networkModel.getNetworkRate(network.getId(), vm.getId()),
                        _networkModel.isSecurityGroupSupportedInNetwork(network), _networkModel.getNetworkTag(vmProfile.getVirtualMachine().getHypervisorType(), network));

        //1) Unplug the nic
        if (vm.getState() == State.Running) {
            final NicTO nicTO = toNicTO(nicProfile, vmProfile.getVirtualMachine().getHypervisorType());
            s_logger.debug("Un-plugging nic " + nic + " for vm " + vm + " from network " + network);
            final boolean result = unplugNic(network, nicTO, vmTO, context, dest);
            if (result) {
                s_logger.debug("Nic is unplugged successfully for vm " + vm + " in network " + network);
                final long isDefault = nic.isDefaultNic() ? 1 : 0;
                UsageEventUtils.publishUsageEvent(EventTypes.EVENT_NETWORK_OFFERING_REMOVE, vm.getAccountId(), vm.getDataCenterId(), vm.getId(),
                        Long.toString(nic.getId()), network.getNetworkOfferingId(), null, isDefault, VirtualMachine.class.getName(), vm.getUuid(), vm.isDisplay());
            } else {
                s_logger.warn("Failed to unplug nic for the vm " + vm + " from network " + network);
                return false;
            }
        } else if (vm.getState() != State.Stopped) {
            s_logger.warn("Unable to remove vm " + vm + " from network  " + network);
            throw new ResourceUnavailableException("Unable to remove vm " + vm + " from network, is not in the right state", DataCenter.class, vm.getDataCenterId());
        }

        //2) Release the nic
        _networkMgr.releaseNic(vmProfile, nic);
        s_logger.debug("Successfully released nic " + nic + "for vm " + vm);

        //3) Remove the nic
        _networkMgr.removeNic(vmProfile, nic);
        _nicsDao.expunge(nic.getId());
        return true;
    }

};