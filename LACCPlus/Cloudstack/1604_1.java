//,temp,AgentManagerImpl.java,1062,1076,temp,AgentManagerImpl.java,734,747
//,3
public class xxx {
    protected AgentAttache createAttacheForConnect(final HostVO host, final Link link) throws ConnectionException {
        s_logger.debug("create ConnectedAgentAttache for " + host.getId());
        final AgentAttache attache = new ConnectedAgentAttache(this, host.getId(), host.getName(), link, host.isInMaintenanceStates());
        link.attach(attache);

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