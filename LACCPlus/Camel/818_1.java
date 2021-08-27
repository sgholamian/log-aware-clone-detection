//,temp,JMXConsumer.java,216,232,temp,JMXConsumer.java,169,185
//,3
public class xxx {
    protected void scheduleReconnect() {
        Runnable startRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    initNetworkConnection();
                    addNotificationListener();
                } catch (Exception e) {
                    LOG.warn("Failed to reconnect to JMX server. >> {}", e.getMessage());
                    scheduleReconnect();
                }
            }
        };
        LOG.info("Delaying JMX consumer reconnection for endpoint {}. Trying again in {} seconds.",
                URISupport.sanitizeUri(mJmxEndpoint.getEndpointUri()), mJmxEndpoint.getReconnectDelay());
        getExecutor().schedule(startRunnable, mJmxEndpoint.getReconnectDelay(), TimeUnit.SECONDS);
    }

};