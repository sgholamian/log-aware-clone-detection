//,temp,HypervInvestigator.java,53,76,temp,XenServerInvestigator.java,50,75
//,3
public class xxx {
    @Override
    public Status isAgentAlive(Host agent) {
        if (agent.getHypervisorType() != Hypervisor.HypervisorType.Hyperv) {
            return null;
        }
        CheckOnHostCommand cmd = new CheckOnHostCommand(agent);
        List<HostVO> neighbors = _resourceMgr.listHostsInClusterByStatus(agent.getClusterId(), Status.Up);
        for (HostVO neighbor : neighbors) {
            if (neighbor.getId() == agent.getId() || neighbor.getHypervisorType() != Hypervisor.HypervisorType.Hyperv) {
                continue;
            }

            try {
                Answer answer = _agentMgr.easySend(neighbor.getId(), cmd);
                if (answer != null) {
                    return answer.getResult() ? Status.Down : Status.Up;
                }
            } catch (Exception e) {
                s_logger.debug("Failed to send command to host: " + neighbor.getId(), e);
            }
        }

        return null;
    }

};