//,temp,KubernetesNamespacesConsumer.java,90,122,temp,KubernetesNodesConsumer.java,90,127
//,3
public class xxx {
        @Override
        public void run() {
            NonNamespaceOperation<Node, NodeList, Resource<Node>> w = getEndpoint().getKubernetesClient().nodes();
            if (ObjectHelper.isNotEmpty(getEndpoint().getKubernetesConfiguration().getLabelKey())
                    && ObjectHelper.isNotEmpty(getEndpoint().getKubernetesConfiguration().getLabelValue())) {
                w.withLabel(getEndpoint().getKubernetesConfiguration().getLabelKey(),
                        getEndpoint().getKubernetesConfiguration().getLabelValue());
            }
            if (ObjectHelper.isNotEmpty(getEndpoint().getKubernetesConfiguration().getResourceName())) {
                w.withName(getEndpoint().getKubernetesConfiguration().getResourceName());
            }
            watch = w.watch(new Watcher<Node>() {

                @Override
                public void eventReceived(io.fabric8.kubernetes.client.Watcher.Action action, Node resource) {
                    NodeEvent ne = new NodeEvent(action, resource);
                    Exchange exchange = createExchange(false);
                    exchange.getIn().setBody(ne.getNode());
                    exchange.getIn().setHeader(KubernetesConstants.KUBERNETES_EVENT_ACTION, ne.getAction());
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