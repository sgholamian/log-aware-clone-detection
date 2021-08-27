//,temp,JmsEndpoint.java,442,450,temp,JmsEndpoint.java,432,440
//,2
public class xxx {
    @Override
    public void shutdown() {
        int running = runningMessageListeners.get();
        if (running <= 0) {
            super.shutdown();
        } else {
            LOG.trace("There are still {} running message listeners. Cannot shutdown endpoint {}", running, this);
        }
    }

};