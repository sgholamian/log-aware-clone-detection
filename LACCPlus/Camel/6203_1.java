//,temp,BaseService.java,205,222,temp,BaseService.java,74,93
//,3
public class xxx {
    public void resume() {
        synchronized (lock) {
            if (status != SUSPENDED) {
                LOG.trace("Service is not suspended: {}", this);
                return;
            }
            status = STARTING;
            LOG.trace("Resuming service: {}", this);
            try (AutoCloseable ignored = doLifecycleChange()) {
                doResume();
                status = STARTED;
                LOG.trace("Resumed service: {}", this);
            } catch (Exception e) {
                LOG.trace("Error while resuming service: {}", this, e);
                fail(e);
            }
        }
    }

};