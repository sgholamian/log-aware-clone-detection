//,temp,ClusteredAgentManagerImpl.java,789,797,temp,Agent.java,1018,1028
//,3
public class xxx {
        @Override
        public synchronized boolean cancel() {
            if (!cancelled) {
                cancelled = true;
                s_logger.debug("Agent load balancer task cancelled");
                return super.cancel();
            }
            return true;
        }

};