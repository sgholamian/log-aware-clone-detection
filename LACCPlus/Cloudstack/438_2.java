//,temp,Ovm3Investigator.java,59,82,temp,SimulatorInvestigator.java,58,85
//,3
public class xxx {
    @Override
    public Status isAgentAlive(Host agent) {
        if (agent.getHypervisorType() != HypervisorType.Simulator) {
            return null;
        }

        if (haManager.isHAEligible(agent)) {
            return haManager.getHostStatus(agent);
        }

        CheckOnHostCommand cmd = new CheckOnHostCommand(agent);
        List<HostVO> neighbors = _resourceMgr.listHostsInClusterByStatus(agent.getClusterId(), Status.Up);
        for (HostVO neighbor : neighbors) {
            if (neighbor.getId() == agent.getId() || neighbor.getHypervisorType() != Hypervisor.HypervisorType.Simulator) {
                continue;
            }
            try {
                Answer answer = _agentMgr.easySend(neighbor.getId(), cmd);
                if (answer != null) {
                    return answer.getResult() ? Status.Up : Status.Down;
                }
            } catch (Exception e) {
                s_logger.debug("Failed to send command to host: " + neighbor.getId());
            }
        }

        return null;
    }

};