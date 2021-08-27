//,temp,AmqpInactivityMonitor.java,58,79,temp,MQTTInactivityMonitor.java,98,139
//,3
public class xxx {
        @Override
        public void run() {

            long now = System.currentTimeMillis();
            int currentCounter = next.getReceiveCounter();
            int previousCounter = lastReceiveCounter.getAndSet(currentCounter);

            // for the PINGREQ/RESP frames, the currentCounter will be different
            // from previousCounter, and that
            // should be sufficient to indicate the connection is still alive.
            // If there were random data, or something
            // outside the scope of the spec, the wire format unrmarshalling
            // would fail, so we don't need to handle
            // PINGREQ/RESP explicitly here
            if (inReceive.get() || currentCounter != previousCounter) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Command received since last read check.");
                }
                lastReceiveTime = now;
                return;
            }

            if ((now - lastReceiveTime) >= readKeepAliveTime + readGraceTime && readCheckerTask != null && !ASYNC_TASKS.isShutdown()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("No message received since last read check for " + MQTTInactivityMonitor.this.toString() + "! Throwing InactivityIOException.");
                }
                try {
                    ASYNC_TASKS.execute(new Runnable() {
                        @Override
                        public void run() {
                            onException(new InactivityIOException("Channel was inactive for too (>" +
                                        (connectionTimeout) + ") long: " + next.getRemoteAddress()));
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