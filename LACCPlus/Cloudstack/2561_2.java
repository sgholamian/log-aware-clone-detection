//,temp,InternalLoadBalancerVMManagerImpl.java,894,921,temp,ElasticLoadBalancerManagerImpl.java,160,183
//,3
public class xxx {
    private boolean sendCommandsToRouter(final DomainRouterVO elbVm, Commands cmds) throws AgentUnavailableException {
        Answer[] answers = null;
        try {
            answers = _agentMgr.send(elbVm.getHostId(), cmds);
        } catch (OperationTimedoutException e) {
            s_logger.warn("ELB: Timed Out", e);
            throw new AgentUnavailableException("Unable to send commands to virtual elbVm ", elbVm.getHostId(), e);
        }

        if (answers == null) {
            return false;
        }

        if (answers.length != cmds.size()) {
            return false;
        }

        // FIXME: Have to return state for individual command in the future
        if (answers.length > 0) {
            Answer ans = answers[0];
            return ans.getResult();
        }
        return true;
    }

};