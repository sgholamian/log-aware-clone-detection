//,temp,ClusteredAgentManagerImpl.java,934,958,temp,ResourceManagerImpl.java,2558,2580
//,3
public class xxx {
    public Boolean propagateResourceEvent(final long agentId, final ResourceState.Event event) throws AgentUnavailableException {
        final String msPeer = getPeerName(agentId);
        if (msPeer == null) {
            return null;
        }

            s_logger.debug("Propagating resource request event:" + event.toString() + " to agent:" + agentId);
        final Command[] cmds = new Command[1];
        cmds[0] = new PropagateResourceEventCommand(agentId, event);

        final String AnsStr = _clusterMgr.execute(msPeer, agentId, _gson.toJson(cmds), true);
        if (AnsStr == null) {
            throw new AgentUnavailableException(agentId);
        }

        final Answer[] answers = _gson.fromJson(AnsStr, Answer[].class);

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Result for agent change is " + answers[0].getResult());
        }

        return answers[0].getResult();
    }

};