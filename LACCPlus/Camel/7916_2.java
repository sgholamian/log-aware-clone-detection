//,temp,KubernetesReplicationControllersConsumer.java,90,132,temp,KubernetesServicesConsumer.java,90,130
//,2
public class xxx {
        @Override
        public void run() {
            MixedOperation<Service, ServiceList, ServiceResource<Service>> w = getEndpoint().getKubernetesClient().services();
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
            watch = w.watch(new Watcher<Service>() {

                @Override
                public void eventReceived(io.fabric8.kubernetes.client.Watcher.Action action, Service resource) {
                    ServiceEvent se = new ServiceEvent(action, resource);
                    Exchange exchange = createExchange(false);
                    exchange.getIn().setBody(se.getService());
                    exchange.getIn().setHeader(KubernetesConstants.KUBERNETES_EVENT_ACTION, se.getAction());
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