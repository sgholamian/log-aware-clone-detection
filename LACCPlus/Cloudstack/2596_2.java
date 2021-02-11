//,temp,SimulatorInvestigator.java,87,109,temp,CheckOnAgentInvestigator.java,47,66
//,3
public class xxx {
    @Override
    public boolean isVmAlive(VirtualMachine vm, Host host) throws UnknownVM {
        CheckVirtualMachineCommand cmd = new CheckVirtualMachineCommand(vm.getInstanceName());
        try {
            CheckVirtualMachineAnswer answer = (CheckVirtualMachineAnswer)_agentMgr.send(vm.getHostId(), cmd);
            if (!answer.getResult()) {
                s_logger.debug("Unable to get vm state on " + vm.toString());
                throw new UnknownVM();
            }

            s_logger.debug("Agent responded with state " + answer.getState().toString());
            return answer.getState() == PowerState.PowerOn;
        } catch (AgentUnavailableException e) {
            s_logger.debug("Unable to reach the agent for " + vm.toString() + ": " + e.getMessage());
            throw new UnknownVM();
        } catch (OperationTimedoutException e) {
            s_logger.debug("Operation timed out for " + vm.toString() + ": " + e.getMessage());
            throw new UnknownVM();
        }
    }

};