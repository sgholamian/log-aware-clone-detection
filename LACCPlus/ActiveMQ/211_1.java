//,temp,AmqpInactivityMonitor.java,85,115,temp,AbstractInactivityMonitor.java,85,106
//,3
public class xxx {
        @Override
        public void run() {
            if (keepAliveTask != null && !ASYNC_TASKS.isShutdown()) {
                try {
                    ASYNC_TASKS.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                long nextIdleUpdate = amqpTransport.keepAlive();
                                if (nextIdleUpdate > 0) {
                                    synchronized (AmqpInactivityMonitor.this) {
                                        if (keepAliveTask != null) {
                                            keepAliveTask = new SchedulerTimerTask(keepAlive);
                                            KEEPALIVE_TASK_TIMER.schedule(keepAliveTask, nextIdleUpdate);
                                        }
                                    }
                                }
                            } catch (Exception ex) {
                                onException(new InactivityIOException(
                                    "Exception while performing idle checks for connection: " + next.getRemoteAddress()));
                            }
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