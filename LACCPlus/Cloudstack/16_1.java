//,temp,ClusteredAgentManagerImpl.java,1218,1228,temp,ConsoleProxyResource.java,426,441
//,3
public class xxx {
        @Override
        protected void runInContext() {
            try {
                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Rebalancing host id=" + hostId);
                }
                rebalanceHost(hostId, currentOwnerId, futureOwnerId);
            } catch (final Exception e) {
                s_logger.warn("Unable to rebalance host id=" + hostId, e);
            }
        }

};