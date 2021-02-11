//,temp,AgentManagerImpl.java,1021,1030,temp,AgentManagerImpl.java,1010,1019
//,3
public class xxx {
    @Override
    public void notifyMonitorsOfRemovedHost(long hostId, long clusterId) {
        for (final Pair<Integer, Listener> monitor : _hostMonitors) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Sending host removed to listener: " + monitor.second().getClass().getSimpleName());
            }

            monitor.second().processHostRemoved(hostId, clusterId);
        }
    }

};