//,temp,VirtualMachineManagerImpl.java,4027,4063,temp,VirtualMachineManagerImpl.java,3968,3996
//,3
public class xxx {
    @Override
    public boolean replugNic(final Network network, final NicTO nic, final VirtualMachineTO vm, final ReservationContext context, final DeployDestination dest) throws ConcurrentOperationException,
    ResourceUnavailableException, InsufficientCapacityException {
        boolean result = true;

        final VMInstanceVO router = _vmDao.findById(vm.getId());
        if (router.getState() == State.Running) {
            try {
                final ReplugNicCommand replugNicCmd = new ReplugNicCommand(nic, vm.getName(), vm.getType(), vm.getDetails());
                final Commands cmds = new Commands(Command.OnError.Stop);
                cmds.addCommand("replugnic", replugNicCmd);
                _agentMgr.send(dest.getHost().getId(), cmds);
                final ReplugNicAnswer replugNicAnswer = cmds.getAnswer(ReplugNicAnswer.class);
                if (replugNicAnswer == null || !replugNicAnswer.getResult()) {
                    s_logger.warn("Unable to replug nic for vm " + vm.getName());
                    result = false;
                }
            } catch (final OperationTimedoutException e) {
                throw new AgentUnavailableException("Unable to plug nic for router " + vm.getName() + " in network " + network, dest.getHost().getId(), e);
            }
        } else {
            s_logger.warn("Unable to apply ReplugNic, vm " + router + " is not in the right state " + router.getState());

            throw new ResourceUnavailableException("Unable to apply ReplugNic on the backend," + " vm " + vm + " is not in the right state", DataCenter.class,
                    router.getDataCenterId());
        }

        return result;
    }

};