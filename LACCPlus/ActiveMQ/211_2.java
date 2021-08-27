//,temp,AmqpInactivityMonitor.java,85,115,temp,AbstractInactivityMonitor.java,85,106
//,3
public class xxx {
        @Override
        public void run() {
            long now = System.currentTimeMillis();

            if ((now - startTime) >= connectAttemptTimeout && connectCheckerTask != null && !ASYNC_TASKS.isShutdown()) {
                LOG.debug("No connection attempt made in time for {}! Throwing InactivityIOException.", AbstractInactivityMonitor.this.toString());
                try {
                    ASYNC_TASKS.execute(new Runnable() {
                        @Override
                        public void run() {
                            onException(new InactivityIOException(
                                "Channel was inactive (no connection attempt made) for too (>" + (connectAttemptTimeout) + ") long: " + next.getRemoteAddress()));
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