//,temp,ClusteredAgentManagerImpl.java,271,285,temp,AgentManagerImpl.java,1062,1076
//,3
public class xxx {
    @Override
    protected AgentAttache createAttacheForConnect(final HostVO host, final Link link) {
        s_logger.debug("create ClusteredAgentAttache for " + host.getId());
        final AgentAttache attache = new ClusteredAgentAttache(this, host.getId(), host.getName(), link, host.isInMaintenanceStates());
        link.attach(attache);
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