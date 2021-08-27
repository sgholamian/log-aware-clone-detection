//,temp,SjmsConsumer.java,78,105,temp,SjmsConsumer.java,49,76
//,3
public class xxx {
    @Override
    protected void doStop() throws Exception {
        if (listenerContainer != null) {

            if (getEndpoint().isAsyncStopListener()) {
                getEndpoint().getAsyncStartStopExecutorService().submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            stopAndDestroyListenerContainer();
                        } catch (Exception e) {
                            LOG.warn("Error stopping listener container on destination: {}. This exception will be ignored.",
                                    getDestinationName(), e);
                        }
                    }

                    @Override
                    public String toString() {
                        return "AsyncStopListenerTask[" + getDestinationName() + "]";
                    }
                });
            } else {
                stopAndDestroyListenerContainer();
            }
        }

        super.doStop();
    }

};