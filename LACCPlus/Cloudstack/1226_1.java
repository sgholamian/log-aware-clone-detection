//,temp,ClusteredAgentManagerImpl.java,934,958,temp,ResourceManagerImpl.java,2558,2580
//,3
public class xxx {
    public Boolean propagateAgentEvent(final long agentId, final Event event) throws AgentUnavailableException {
        final String msPeer = getPeerName(agentId);
        if (msPeer == null) {
            return null;
        }

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Propagating agent change request event:" + event.toString() + " to agent:" + agentId);
        }
        final Command[] cmds = new Command[1];
        cmds[0] = new ChangeAgentCommand(agentId, event);

        final String ansStr = _clusterMgr.execute(msPeer, agentId, _gson.toJson(cmds), true);
        if (ansStr == null) {
            throw new AgentUnavailableException(agentId);
        }

        final Answer[] answers = _gson.fromJson(ansStr, Answer[].class);

        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Result for agent change is " + answers[0].getResult());
        }

        return answers[0].getResult();
    }

};