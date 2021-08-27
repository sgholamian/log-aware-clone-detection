//,temp,JmsEndpoint.java,442,450,temp,JmsEndpoint.java,432,440
//,2
public class xxx {
    @Override
    public void stop() {
        int running = runningMessageListeners.get();
        if (running <= 0) {
            super.stop();
        } else {
            LOG.trace("There are still {} running message listeners. Cannot stop endpoint {}", running, this);
        }
    }

};