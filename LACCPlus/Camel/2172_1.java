//,temp,KubernetesDeploymentsConsumer.java,90,129,temp,KubernetesPodsConsumer.java,90,130
//,3
public class xxx {
        @Override
        public void run() {
            MixedOperation<Deployment, DeploymentList, RollableScalableResource<Deployment>> w
                    = getEndpoint().getKubernetesClient()
                            .apps().deployments();
            if (ObjectHelper.isNotEmpty(getEndpoint().getKubernetesConfiguration().getLabelKey())
                    && ObjectHelper.isNotEmpty(getEndpoint().getKubernetesConfiguration().getLabelValue())) {
                w.withLabel(getEndpoint().getKubernetesConfiguration().getLabelKey(),
                        getEndpoint().getKubernetesConfiguration().getLabelValue());
            }
            if (ObjectHelper.isNotEmpty(getEndpoint().getKubernetesConfiguration().getResourceName())) {
                w.withName(getEndpoint().getKubernetesConfiguration().getResourceName());
            }
            watch = w.watch(new Watcher<Deployment>() {

                @Override
                public void eventReceived(io.fabric8.kubernetes.client.Watcher.Action action, Deployment resource) {
                    DeploymentEvent de = new DeploymentEvent(action, resource);
                    Exchange exchange = createExchange(false);
                    exchange.getIn().setBody(de.getDeployment());
                    exchange.getIn().setHeader(KubernetesConstants.KUBERNETES_EVENT_ACTION, de.getAction());
                    exchange.getIn().setHeader(KubernetesConstants.KUBERNETES_EVENT_TIMESTAMP, System.currentTimeMillis());
                    try {
                        processor.process(exchange);
                    } catch (Exception e) {
                        getExceptionHandler().handleException("Error during processing", exchange, e);
                    } finally {
                        releaseExchange(exchange, false);
                    }
                }

                @Override
                public void onClose(WatcherException cause) {
                    if (cause != null) {
                        LOG.error(cause.getMessage(), cause);
                    }

                }
            });
        }

};