//,temp,KubernetesCustomResourcesConsumer.java,63,81,temp,KubernetesNodesConsumer.java,65,84
//,2
public class xxx {
    @Override
    protected void doStop() throws Exception {
        LOG.debug("Stopping Kubernetes Custom Resources Consumer");
        if (executor != null) {
            if (getEndpoint() != null && getEndpoint().getCamelContext() != null) {
                if (customResourcesWatcher != null) {
                    customResourcesWatcher.getWatch().close();
                }
                getEndpoint().getCamelContext().getExecutorServiceManager().shutdownNow(executor);
            } else {
                if (customResourcesWatcher != null) {
                    customResourcesWatcher.getWatch().close();
                }
                executor.shutdownNow();
            }
        }
        executor = null;
        super.doStop();
    }

};