//,temp,BaseService.java,230,252,temp,ChildServiceSupport.java,72,96
//,3
public class xxx {
    public void shutdown() {
        synchronized (lock) {
            if (status == SHUTDOWN) {
                LOG.trace("Service: {} already shutdown", this);
                return;
            }
            if (status == SHUTTING_DOWN) {
                LOG.trace("Service: {} already shutting down", this);
                return;
            }
            stop();
            status = SHUTDOWN;
            LOG.trace("Shutting down service: {}", this);
            try (AutoCloseable ignored = doLifecycleChange()) {
                doShutdown();
                LOG.trace("Shutdown service: {}", this);
                status = SHUTDOWN;
            } catch (Exception e) {
                LOG.trace("Error shutting down service: {}", this, e);
                fail(e);
            }
        }
    }

};