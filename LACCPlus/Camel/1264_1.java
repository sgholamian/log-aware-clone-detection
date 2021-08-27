//,temp,BaseService.java,143,168,temp,BaseService.java,101,135
//,3
public class xxx {
    public void stop() {
        synchronized (lock) {
            if (status == FAILED) {
                LOG.trace("Service: {} failed and regarded as already stopped", this);
                return;
            }
            if (status == STOPPED || status == SHUTTING_DOWN || status == SHUTDOWN) {
                LOG.trace("Service: {} already stopped", this);
                return;
            }
            if (status == STOPPING) {
                LOG.trace("Service: {} already stopping", this);
                return;
            }
            status = STOPPING;
            LOG.trace("Stopping service: {}", this);
            try (AutoCloseable ignored = doLifecycleChange()) {
                doStop();
                status = STOPPED;
                LOG.trace("Stopped: {} service", this);
            } catch (Exception e) {
                LOG.trace("Error while stopping service: {}", this, e);
                fail(e);
            }
        }
    }

};