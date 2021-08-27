//,temp,KubernetesDeploymentsConsumer.java,90,129,temp,KubernetesPodsConsumer.java,90,130
//,3
public class xxx {
        @Override
        public void run() {
            MixedOperation<Pod, PodList, PodResource<Pod>> w = getEndpoint().getKubernetesClient().pods();
            if (ObjectHelper.isNotEmpty(getEndpoint().getKubernetesConfiguration().getNamespace())) {
                w.inNamespace(getEndpoint().getKubernetesConfiguration().getNamespace());
            }
            if (ObjectHelper.isNotEmpty(getEndpoint().getKubernetesConfiguration().getLabelKey())
                    && ObjectHelper.isNotEmpty(getEndpoint().getKubernetesConfiguration().getLabelValue())) {
                w.withLabel(getEndpoint().getKubernetesConfiguration().getLabelKey(),
                        getEndpoint().getKubernetesConfiguration().getLabelValue());
            }
            if (ObjectHelper.isNotEmpty(getEndpoint().getKubernetesConfiguration().getResourceName())) {
                w.withName(getEndpoint().getKubernetesConfiguration().getResourceName());
            }
            watch = w.watch(new Watcher<Pod>() {

                @Override
                public void eventReceived(io.fabric8.kubernetes.client.Watcher.Action action, Pod resource) {
                    PodEvent pe = new PodEvent(action, resource);
                    Exchange exchange = createExchange(false);
                    exchange.getIn().setBody(pe.getPod());
                    exchange.getIn().setHeader(KubernetesConstants.KUBERNETES_EVENT_ACTION, pe.getAction());
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