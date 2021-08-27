//,temp,BaseService.java,176,197,temp,ChildServiceSupport.java,98,123
//,3
public class xxx {
    public void suspend() {
        synchronized (lock) {
            if (status == SUSPENDED) {
                LOG.trace("Service: {} already suspended", this);
                return;
            }
            if (status == SUSPENDING) {
                LOG.trace("Service: {} already suspending", this);
                return;
            }
            status = SUSPENDING;
            LOG.trace("Suspending service: {}", this);
            try (AutoCloseable ignored = doLifecycleChange()) {
                doSuspend();
                status = SUSPENDED;
                LOG.trace("Suspended service: {}", this);
            } catch (Exception e) {
                LOG.trace("Error while suspending service: {}", this, e);
                fail(e);
            }
        }
    }

};