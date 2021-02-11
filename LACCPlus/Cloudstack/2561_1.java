//,temp,InternalLoadBalancerVMManagerImpl.java,894,921,temp,ElasticLoadBalancerManagerImpl.java,160,183
//,3
public class xxx {
    protected boolean sendCommandsToInternalLbVm(final VirtualRouter internalLbVm, final Commands cmds) throws AgentUnavailableException {
        Answer[] answers = null;
        try {
            answers = _agentMgr.send(internalLbVm.getHostId(), cmds);
        } catch (final OperationTimedoutException e) {
            s_logger.warn("Timed Out", e);
            throw new AgentUnavailableException("Unable to send commands to virtual router ", internalLbVm.getHostId(), e);
        }

        if (answers == null) {
            return false;
        }

        if (answers.length != cmds.size()) {
            return false;
        }

        boolean result = true;
        if (answers.length > 0) {
            for (final Answer answer : answers) {
                if (!answer.getResult()) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

};