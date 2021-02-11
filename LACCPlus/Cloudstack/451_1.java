//,temp,VirtualMachineManagerImpl.java,3998,4025,temp,VirtualMachineManagerImpl.java,3968,3996
//,3
public class xxx {
    public boolean plugNic(final Network network, final NicTO nic, final VirtualMachineTO vm, final ReservationContext context, final DeployDestination dest) throws ConcurrentOperationException,
    ResourceUnavailableException, InsufficientCapacityException {
        boolean result = true;

        final VMInstanceVO router = _vmDao.findById(vm.getId());
        if (router.getState() == State.Running) {
            try {
                final PlugNicCommand plugNicCmd = new PlugNicCommand(nic, vm.getName(), vm.getType(), vm.getDetails());
                final Commands cmds = new Commands(Command.OnError.Stop);
                cmds.addCommand("plugnic", plugNicCmd);
                _agentMgr.send(dest.getHost().getId(), cmds);
                final PlugNicAnswer plugNicAnswer = cmds.getAnswer(PlugNicAnswer.class);
                if (plugNicAnswer == null || !plugNicAnswer.getResult()) {
                    s_logger.warn("Unable to plug nic for vm " + vm.getName());
                    result = false;
                }
            } catch (final OperationTimedoutException e) {
                throw new AgentUnavailableException("Unable to plug nic for router " + vm.getName() + " in network " + network, dest.getHost().getId(), e);
            }
        } else {
            s_logger.warn("Unable to apply PlugNic, vm " + router + " is not in the right state " + router.getState());

            throw new ResourceUnavailableException("Unable to apply PlugNic on the backend," + " vm " + vm + " is not in the right state", DataCenter.class,
                    router.getDataCenterId());
        }

        return result;
    }

};