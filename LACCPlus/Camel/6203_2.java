//,temp,BaseService.java,205,222,temp,BaseService.java,74,93
//,3
public class xxx {
    public void init() {
        // allow to initialize again if stopped or failed
        if (status <= BUILT || status >= STOPPED) {
            synchronized (lock) {
                if (status <= BUILT || status >= STOPPED) {
                    build();
                    LOG.trace("Initializing service: {}", this);
                    try (AutoCloseable ignored = doLifecycleChange()) {
                        status = INITIALIZING;
                        doInit();
                        status = INITIALIZED;
                        LOG.trace("Initialized service: {}", this);
                    } catch (Exception e) {
                        LOG.trace("Error while initializing service: {}", this, e);
                        fail(e);
                    }
                }
            }
        }
    }

};