//,temp,ClusteredAgentManagerImpl.java,287,300,temp,ClusteredAgentManagerImpl.java,271,285
//,3
public class xxx {
    @Override
    protected AgentAttache createAttacheForDirectConnect(final Host host, final ServerResource resource) {
        s_logger.debug("create ClusteredDirectAgentAttache for " + host.getId());
        final DirectAgentAttache attache = new ClusteredDirectAgentAttache(this, host.getId(), host.getName(), _nodeId, resource, host.isInMaintenanceStates());
        AgentAttache old = null;
        synchronized (_agents) {
            old = _agents.get(host.getId());
            _agents.put(host.getId(), attache);
        }
        if (old != null) {
            old.disconnect(Status.Removed);
        }
        return attache;
    }

};