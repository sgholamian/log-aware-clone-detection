//,temp,NetworkUsageManagerImpl.java,478,484,temp,MockAgentManagerImpl.java,517,523
//,3
public class xxx {
        @Override
        public boolean processDisconnect(long agentId, Status state) {
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Disconnected called on " + agentId + " with status " + state.toString());
            }
            return true;
        }

};