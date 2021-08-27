//,temp,SjmsConsumer.java,78,105,temp,SjmsConsumer.java,49,76
//,3
public class xxx {
    @Override
    protected void doStart() throws Exception {
        super.doStart();

        if (getEndpoint().isAsyncStartListener()) {
            getEndpoint().getAsyncStartStopExecutorService().submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        prepareAndStartListenerContainer();
                    } catch (Exception e) {
                        LOG.warn("Error starting listener container on destination: {}. This exception will be ignored.",
                                getDestinationName(), e);
                    }
                }

                @Override
                public String toString() {
                    return "AsyncStartListenerTask[" + getDestinationName() + "]";
                }
            });
        } else {
            prepareAndStartListenerContainer();
        }

        // mark as initialized for the first time
        initialized = true;
    }

};