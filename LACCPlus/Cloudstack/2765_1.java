//,temp,VirtualMachineManagerImpl.java,3623,3708,temp,VirtualMachineManagerImpl.java,3453,3512
//,3
public class xxx {
    @DB
    private boolean orchestrateRemoveVmFromNetwork(final VirtualMachine vm, final Network network, final URI broadcastUri) throws ConcurrentOperationException, ResourceUnavailableException {
        final CallContext cctx = CallContext.current();
        final VMInstanceVO vmVO = _vmDao.findById(vm.getId());
        final ReservationContext context = new ReservationContextImpl(null, null, cctx.getCallingUser(), cctx.getCallingAccount());

        final VirtualMachineProfileImpl vmProfile = new VirtualMachineProfileImpl(vmVO, null, null, null, null);

        final DataCenter dc = _entityMgr.findById(DataCenter.class, network.getDataCenterId());
        final Host host = _hostDao.findById(vm.getHostId());
        final DeployDestination dest = new DeployDestination(dc, null, null, host);
        final HypervisorGuru hvGuru = _hvGuruMgr.getGuru(vmProfile.getVirtualMachine().getHypervisorType());
        final VirtualMachineTO vmTO = hvGuru.implement(vmProfile);

        Nic nic = null;
        if (broadcastUri != null) {
            nic = _nicsDao.findByNetworkIdInstanceIdAndBroadcastUri(network.getId(), vm.getId(), broadcastUri.toString());
        } else {
            nic = _networkModel.getNicInNetwork(vm.getId(), network.getId());
        }

        if (nic == null) {
            s_logger.warn("Could not get a nic with " + network);
            return false;
        }

        // don't delete default NIC on a user VM
        if (nic.isDefaultNic() && vm.getType() == VirtualMachine.Type.User) {
            s_logger.warn("Failed to remove nic from " + vm + " in " + network + ", nic is default.");
            throw new CloudRuntimeException("Failed to remove nic from " + vm + " in " + network + ", nic is default.");
        }

        //Lock on nic is needed here
        final Nic lock = _nicsDao.acquireInLockTable(nic.getId());
        if (lock == null) {
            //check if nic is still there. Return if it was released already
            if (_nicsDao.findById(nic.getId()) == null) {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Not need to remove the vm " + vm + " from network " + network + " as the vm doesn't have nic in this network");
                }
                return true;
            }
            throw new ConcurrentOperationException("Unable to lock nic " + nic.getId());
        }

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Lock is acquired for nic id " + lock.getId() + " as a part of remove vm " + vm + " from network " + network);
        }

        try {
            final NicProfile nicProfile =
                    new NicProfile(nic, network, nic.getBroadcastUri(), nic.getIsolationUri(), _networkModel.getNetworkRate(network.getId(), vm.getId()),
                            _networkModel.isSecurityGroupSupportedInNetwork(network), _networkModel.getNetworkTag(vmProfile.getVirtualMachine().getHypervisorType(), network));

            //1) Unplug the nic
            if (vm.getState() == State.Running) {
                final NicTO nicTO = toNicTO(nicProfile, vmProfile.getVirtualMachine().getHypervisorType());
                s_logger.debug("Un-plugging nic for vm " + vm + " from network " + network);
                final boolean result = unplugNic(network, nicTO, vmTO, context, dest);
                if (result) {
                    s_logger.debug("Nic is unplugged successfully for vm " + vm + " in network " + network);
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
            return true;
        } finally {
            if (lock != null) {
                _nicsDao.releaseFromLockTable(lock.getId());
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Lock is released for nic id " + lock.getId() + " as a part of remove vm " + vm + " from network " + network);
                }
            }
        }
    }

};