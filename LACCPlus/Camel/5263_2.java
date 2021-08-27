//,temp,ConsulClusterView.java,263,273,temp,ConsulClusterView.java,111,123
//,3
public class xxx {
    @Override
    protected void doStop() throws Exception {
        if (sessionId.get() != null) {
            if (keyValueClient.releaseLock(this.path, sessionId.get())) {
                LOGGER.debug("Successfully released lock on path '{}' with id '{}'", path, sessionId.get());
            }

            synchronized (sessionId) {
                sessionClient.destroySession(sessionId.getAndSet(null));
                localMember.setMaster(false);
            }
        }
    }

};