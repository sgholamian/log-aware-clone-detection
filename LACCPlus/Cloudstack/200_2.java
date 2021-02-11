//,temp,ClusteredAgentManagerImpl.java,271,285,temp,AgentManagerImpl.java,734,747
//,3
public class xxx {
    protected AgentAttache createAttacheForDirectConnect(final Host host, final ServerResource resource) throws ConnectionException {
        s_logger.debug("create DirectAgentAttache for " + host.getId());
        final DirectAgentAttache attache = new DirectAgentAttache(this, host.getId(), host.getName(), resource, host.isInMaintenanceStates());

        AgentAttache old = null;
        synchronized (_agents) {
            old = _agents.put(host.getId(), attache);
        }
        if (old != null) {
            old.disconnect(Status.Removed);
        }

        return attache;
    }

};