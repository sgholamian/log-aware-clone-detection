//,temp,AmqpInactivityMonitor.java,58,79,temp,MQTTInactivityMonitor.java,98,139
//,3
public class xxx {
        @Override
        public void run() {
            long now = System.currentTimeMillis();

            if ((now - startTime) >= connectionTimeout && connectCheckerTask != null && !ASYNC_TASKS.isShutdown()) {
                LOG.debug("No connection attempt made in time for {}! Throwing InactivityIOException.", AmqpInactivityMonitor.this.toString());
                try {
                    ASYNC_TASKS.execute(new Runnable() {
                        @Override
                        public void run() {
                            onException(new InactivityIOException(
                                "Channel was inactive for too (>" + (connectionTimeout) + ") long: " + next.getRemoteAddress()));
                        }
                    });
                } catch (RejectedExecutionException ex) {
                    if (!ASYNC_TASKS.isShutdown()) {
                        LOG.error("Async connection timeout task was rejected from the executor: ", ex);
                        throw ex;
                    }
                }
            }
        }

};