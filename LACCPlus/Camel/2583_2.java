//,temp,KubernetesHPAConsumer.java,90,133,temp,KubernetesConfigMapsConsumer.java,90,133
//,3
public class xxx {
        @Override
        public void run() {

            FilterWatchListDeletable<ConfigMap, ConfigMapList> w = null;
            if (ObjectHelper.isNotEmpty(getEndpoint().getKubernetesConfiguration().getLabelKey())
                    && ObjectHelper.isNotEmpty(getEndpoint().getKubernetesConfiguration().getLabelValue())) {
                w = getEndpoint().getKubernetesClient().configMaps().withLabel(
                        getEndpoint().getKubernetesConfiguration().getLabelKey(),
                        getEndpoint().getKubernetesConfiguration().getLabelValue());
            }
            if (ObjectHelper.isNotEmpty(getEndpoint().getKubernetesConfiguration().getResourceName())) {
                w = (FilterWatchListDeletable<ConfigMap, ConfigMapList>) getEndpoint()
                        .getKubernetesClient().configMaps()
                        .withName(getEndpoint().getKubernetesConfiguration().getResourceName());
            }
            if (w == null) {
                throw new RuntimeCamelException("Consumer label key or consumer resource name need to be set.");
            }
            watch = w.watch(new Watcher<ConfigMap>() {

                @Override
                public void eventReceived(io.fabric8.kubernetes.client.Watcher.Action action, ConfigMap resource) {
                    ConfigMapEvent de = new ConfigMapEvent(action, resource);
                    Exchange exchange = createExchange(false);
                    exchange.getIn().setBody(de.getConfigMap());
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