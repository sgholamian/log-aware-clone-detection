//,temp,KubernetesHPAConsumer.java,90,133,temp,KubernetesConfigMapsConsumer.java,90,133
//,3
public class xxx {
        @Override
        public void run() {
            MixedOperation<HorizontalPodAutoscaler, HorizontalPodAutoscalerList, Resource<HorizontalPodAutoscaler>> w
                    = getEndpoint()
                            .getKubernetesClient().autoscaling().v1().horizontalPodAutoscalers();
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
            watch = w.watch(new Watcher<HorizontalPodAutoscaler>() {

                @Override
                public void eventReceived(
                        io.fabric8.kubernetes.client.Watcher.Action action, HorizontalPodAutoscaler resource) {
                    HPAEvent hpae = new HPAEvent(action, resource);
                    Exchange exchange = createExchange(false);
                    exchange.getIn().setBody(hpae.getHpa());
                    exchange.getIn().setHeader(KubernetesConstants.KUBERNETES_EVENT_ACTION, hpae.getAction());
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