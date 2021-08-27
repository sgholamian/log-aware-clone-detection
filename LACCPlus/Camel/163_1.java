//,temp,KubernetesPodsConsumer.java,65,84,temp,KubernetesNamespacesConsumer.java,65,84
//,2
public class xxx {
    @Override
    protected void doStop() throws Exception {
        super.doStop();

        LOG.debug("Stopping Kubernetes Pods Consumer");
        if (executor != null) {
            if (getEndpoint() != null && getEndpoint().getCamelContext() != null) {
                if (podsWatcher != null) {
                    podsWatcher.getWatch().close();
                }
                getEndpoint().getCamelContext().getExecutorServiceManager().shutdownNow(executor);
            } else {
                if (podsWatcher != null) {
                    podsWatcher.getWatch().close();
                }
                executor.shutdownNow();
            }
        }
        executor = null;
    }

};