//,temp,BaseService.java,143,168,temp,BaseService.java,101,135
//,3
public class xxx {
    public void start() {
        synchronized (lock) {
            if (status == STARTED) {
                LOG.trace("Service: {} already started", this);
                return;
            }
            if (status == STARTING) {
                LOG.trace("Service: {} already starting", this);
                return;
            }
            init();
            if (status == FAILED) {
                LOG.trace("Init failed");
                return;
            }
            try (AutoCloseable ignored = doLifecycleChange()) {
                status = STARTING;
                LOG.trace("Starting service: {}", this);
                doStart();
                status = STARTED;
                LOG.trace("Started service: {}", this);
            } catch (Exception e) {
                // need to stop as some resources may have been started during startup
                try {
                    stop();
                } catch (Exception e2) {
                    // ignore
                    LOG.trace("Error while stopping service after it failed to start: {}. This exception is ignored",
                            this, e);
                }
                LOG.trace("Error while starting service: {}", this, e);
                fail(e);
            }
        }
    }

};