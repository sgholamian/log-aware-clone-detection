//,temp,KubernetesReplicationControllersConsumer.java,65,84,temp,KubernetesDeploymentsConsumer.java,65,84
//,2
public class xxx {
    @Override
    protected void doStop() throws Exception {
        super.doStop();

        LOG.debug("Stopping Kubernetes Replication Controllers Consumer");
        if (executor != null) {
            if (getEndpoint() != null && getEndpoint().getCamelContext() != null) {
                if (rcWatcher != null) {
                    rcWatcher.getWatch().close();
                }
                getEndpoint().getCamelContext().getExecutorServiceManager().shutdownNow(executor);
            } else {
                if (rcWatcher != null) {
                    rcWatcher.getWatch().close();
                }
                executor.shutdownNow();
            }
        }
        executor = null;
    }

};