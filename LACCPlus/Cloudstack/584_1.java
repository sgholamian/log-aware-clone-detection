//,temp,VirtualMachineManagerImpl.java,4027,4063,temp,VpcVirtualNetworkApplianceManagerImpl.java,208,234
//,3
public class xxx {
    public boolean unplugNic(final Network network, final NicTO nic, final VirtualMachineTO vm, final ReservationContext context, final DeployDestination dest) throws ConcurrentOperationException,
    ResourceUnavailableException {

        boolean result = true;
        final VMInstanceVO router = _vmDao.findById(vm.getId());

        if (router.getState() == State.Running) {
            // collect vm network statistics before unplug a nic
            UserVmVO userVm = _userVmDao.findById(vm.getId());
            if (userVm != null && userVm.getType() == VirtualMachine.Type.User) {
                _userVmService.collectVmNetworkStatistics(userVm);
            }
            try {
                final Commands cmds = new Commands(Command.OnError.Stop);
                final UnPlugNicCommand unplugNicCmd = new UnPlugNicCommand(nic, vm.getName());
                cmds.addCommand("unplugnic", unplugNicCmd);
                _agentMgr.send(dest.getHost().getId(), cmds);

                final UnPlugNicAnswer unplugNicAnswer = cmds.getAnswer(UnPlugNicAnswer.class);
                if (unplugNicAnswer == null || !unplugNicAnswer.getResult()) {
                    s_logger.warn("Unable to unplug nic from router " + router);
                    result = false;
                }
            } catch (final OperationTimedoutException e) {
                throw new AgentUnavailableException("Unable to unplug nic from rotuer " + router + " from network " + network, dest.getHost().getId(), e);
            }
        } else if (router.getState() == State.Stopped || router.getState() == State.Stopping) {
            s_logger.debug("Vm " + router.getInstanceName() + " is in " + router.getState() + ", so not sending unplug nic command to the backend");
        } else {
            s_logger.warn("Unable to apply unplug nic, Vm " + router + " is not in the right state " + router.getState());

            throw new ResourceUnavailableException("Unable to apply unplug nic on the backend," + " vm " + router + " is not in the right state", DataCenter.class,
                    router.getDataCenterId());
        }

        return result;
    }

};