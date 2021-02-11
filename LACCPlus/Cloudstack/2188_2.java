//,temp,AgentManagerImpl.java,1021,1030,temp,AgentManagerImpl.java,541,550
//,3
public class xxx {
    @Override
    public void notifyMonitorsOfNewlyAddedHost(long hostId) {
        for (final Pair<Integer, Listener> monitor : _hostMonitors) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Sending host added to listener: " + monitor.second().getClass().getSimpleName());
            }

            monitor.second().processHostAdded(hostId);
        }
    }

};