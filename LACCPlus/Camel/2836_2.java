//,temp,KubernetesCustomResourcesConsumer.java,63,81,temp,KubernetesNodesConsumer.java,65,84
//,2
public class xxx {
    @Override
    protected void doStop() throws Exception {
        super.doStop();

        LOG.debug("Stopping Kubernetes Nodes Consumer");
        if (executor != null) {
            if (getEndpoint() != null && getEndpoint().getCamelContext() != null) {
                if (nodesWatcher != null) {
                    nodesWatcher.getWatch().close();
                }
                getEndpoint().getCamelContext().getExecutorServiceManager().shutdownNow(executor);
            } else {
                if (nodesWatcher != null) {
                    nodesWatcher.getWatch().close();
                }
                executor.shutdownNow();
            }
        }
        executor = null;
    }

};