//,temp,BaseService.java,230,252,temp,ChildServiceSupport.java,72,96
//,3
public class xxx {
    @Override
    public void stop() {
        synchronized (lock) {
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
            try {
                doStop();
                ServiceHelper.stopService(childServices);
                status = STOPPED;
                LOG.trace("Service: {} stopped service", this);
            } catch (Exception e) {
                status = FAILED;
                LOG.trace("Error while stopping service: {}", this, e);
                throw RuntimeCamelException.wrapRuntimeCamelException(e);
            }
        }
    }

};