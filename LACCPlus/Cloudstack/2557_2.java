//,temp,HypervInvestigator.java,53,76,temp,XenServerInvestigator.java,50,75
//,3
public class xxx {
    @Override
    public Status isAgentAlive(Host agent) {
        if (agent.getHypervisorType() != HypervisorType.XenServer) {
            return null;
        }

        CheckOnHostCommand cmd = new CheckOnHostCommand(agent);
        List<HostVO> neighbors = _resourceMgr.listAllHostsInCluster(agent.getClusterId());
        for (HostVO neighbor : neighbors) {
            if (neighbor.getId() == agent.getId() || neighbor.getHypervisorType() != HypervisorType.XenServer) {
                continue;
            }
            Answer answer = _agentMgr.easySend(neighbor.getId(), cmd);
            if (answer != null && answer.getResult()) {
                CheckOnHostAnswer ans = (CheckOnHostAnswer)answer;
                if (!ans.isDetermined()) {
                    s_logger.debug("Host " + neighbor + " couldn't determine the status of " + agent);
                    continue;
                }
                // even it returns true, that means host is up, but XAPI may not work
                return ans.isAlive() ? null : Status.Down;
            }
        }

        return null;
    }

};