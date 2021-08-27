//,temp,KubernetesHPAConsumer.java,65,84,temp,KubernetesServicesConsumer.java,66,84
//,2
public class xxx {
    @Override
    protected void doStop() throws Exception {
        super.doStop();

        LOG.debug("Stopping Kubernetes HPA Consumer");
        if (executor != null) {
            if (getEndpoint() != null && getEndpoint().getCamelContext() != null) {
                if (hpasWatcher != null) {
                    hpasWatcher.getWatch().close();
                }
                getEndpoint().getCamelContext().getExecutorServiceManager().shutdownNow(executor);
            } else {
                if (hpasWatcher != null) {
                    hpasWatcher.getWatch().close();
                }
                executor.shutdownNow();
            }
        }
        executor = null;
    }

};