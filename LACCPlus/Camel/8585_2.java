//,temp,KubernetesConfigMapsConsumer.java,65,84,temp,NsqConsumer.java,89,107
//,3
public class xxx {
    @Override
    protected void doStop() throws Exception {

        LOG.debug("Stopping NSQ Consumer");
        if (consumer != null) {
            consumer.shutdown();
        }
        if (executor != null) {
            if (getEndpoint() != null && getEndpoint().getCamelContext() != null) {
                getEndpoint().getCamelContext().getExecutorServiceManager().shutdownNow(executor);
            } else {
                executor.shutdownNow();
            }
        }

        executor = null;

        super.doStop();
    }

};