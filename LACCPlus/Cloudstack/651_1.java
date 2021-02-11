//,temp,AgentManagerImpl.java,1010,1019,temp,AgentManagerImpl.java,541,550
//,2
public class xxx {
    @Override
    public void notifyMonitorsOfHostAboutToBeRemoved(long hostId) {
        for (final Pair<Integer, Listener> monitor : _hostMonitors) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Sending host about to be removed to listener: " + monitor.second().getClass().getSimpleName());
            }

            monitor.second().processHostAboutToBeRemoved(hostId);
        }
    }

};