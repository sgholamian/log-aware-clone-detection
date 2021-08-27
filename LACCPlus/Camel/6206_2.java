//,temp,BaseService.java,176,197,temp,ChildServiceSupport.java,98,123
//,3
public class xxx {
    @Override
    public void shutdown() {
        synchronized (lock) {
            if (status == SHUTDOWN) {
                LOG.trace("Service: {} already shut down", this);
                return;
            }
            if (status == SHUTTING_DOWN) {
                LOG.trace("Service: {} already shutting down", this);
                return;
            }
            stop();
            status = SHUTDOWN;
            LOG.trace("Shutting down service: {}", this);
            try {
                doShutdown();
                ServiceHelper.stopAndShutdownServices(childServices);
                LOG.trace("Service: {} shut down", this);
                status = SHUTDOWN;
            } catch (Exception e) {
                status = FAILED;
                LOG.trace("Error shutting down service: {}", this, e);
                throw RuntimeCamelException.wrapRuntimeCamelException(e);
            }
        }
    }

};