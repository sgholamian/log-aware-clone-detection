//,temp,KubernetesReplicationControllersConsumer.java,65,84,temp,KubernetesDeploymentsConsumer.java,65,84
//,2
public class xxx {
    @Override
    protected void doStop() throws Exception {
        super.doStop();

        LOG.debug("Stopping Kubernetes Deployments Consumer");
        if (executor != null) {
            if (getEndpoint() != null && getEndpoint().getCamelContext() != null) {
                if (deploymentsWatcher != null) {
                    deploymentsWatcher.getWatch().close();
                }
                getEndpoint().getCamelContext().getExecutorServiceManager().shutdownNow(executor);
            } else {
                if (deploymentsWatcher != null) {
                    deploymentsWatcher.getWatch().close();
                }
                executor.shutdownNow();
            }
        }
        executor = null;
    }

};