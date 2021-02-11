//,temp,Ovm3Investigator.java,59,82,temp,SimulatorInvestigator.java,58,85
//,3
public class xxx {
    @Override
    public Status isAgentAlive(Host agent) {
        LOGGER.debug("isAgentAlive: " + agent.getName());
        if (agent.getHypervisorType() != Hypervisor.HypervisorType.Ovm3) {
            return null;
        }
        CheckOnHostCommand cmd = new CheckOnHostCommand(agent);
        List<HostVO> neighbors = resourceMgr.listHostsInClusterByStatus(agent.getClusterId(), Status.Up);
        for (HostVO neighbor : neighbors) {
            if (neighbor.getId() == agent.getId() || neighbor.getHypervisorType() != Hypervisor.HypervisorType.Ovm3) {
                continue;
            }
            try {
                Answer answer = agentMgr.easySend(neighbor.getId(), cmd);
                if (answer != null) {
                    return answer.getResult() ? Status.Down : Status.Up;
                }
            } catch (Exception e) {
                LOGGER.error("Failed to send command to host: " + neighbor.getId(), e);
            }
        }

        return null;
    }

};