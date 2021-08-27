//,temp,KubernetesNamespacesConsumer.java,90,122,temp,KubernetesNodesConsumer.java,90,127
//,3
public class xxx {
        @Override
        public void run() {
            NonNamespaceOperation<Namespace, NamespaceList, Resource<Namespace>> w
                    = getEndpoint().getKubernetesClient().namespaces();
            if (ObjectHelper.isNotEmpty(getEndpoint().getKubernetesConfiguration().getNamespace())) {
                w.withName(getEndpoint().getKubernetesConfiguration().getNamespace());
            }
            watch = w.watch(new Watcher<Namespace>() {

                @Override
                public void eventReceived(io.fabric8.kubernetes.client.Watcher.Action action, Namespace resource) {
                    NamespaceEvent ne = new NamespaceEvent(action, resource);
                    Exchange exchange = createExchange(false);
                    exchange.getIn().setBody(ne.getNamespace());
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