//,temp,JMXConsumer.java,216,232,temp,JMXConsumer.java,169,185
//,3
public class xxx {
    protected void scheduleDelayedStart() throws Exception {
        Runnable startRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    doStart();
                } catch (Exception e) {
                    LOG.error("An unrecoverable exception has occurred while starting the JMX consumer"
                              + " for endpoint {}",
                            URISupport.sanitizeUri(mJmxEndpoint.getEndpointUri()), e);
                }
            }
        };
        LOG.info("Delaying JMX consumer startup for endpoint {}. Trying again in {} seconds.",
                URISupport.sanitizeUri(mJmxEndpoint.getEndpointUri()), mJmxEndpoint.getReconnectDelay());
        getExecutor().schedule(startRunnable, mJmxEndpoint.getReconnectDelay(), TimeUnit.SECONDS);
    }

};