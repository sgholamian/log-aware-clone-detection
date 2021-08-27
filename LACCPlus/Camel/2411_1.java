//,temp,KubernetesNamespacesProducer.java,93,108,temp,KubernetesHPAProducer.java,95,112
//,3
public class xxx {
    protected void doListNamespaceByLabel(Exchange exchange, String operation) {
        Map<String, String> labels = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_LABELS, Map.class);
        if (ObjectHelper.isEmpty(labels)) {
            LOG.error("Get a specific namespace by labels require specify a labels set");
            throw new IllegalArgumentException("Get a specific namespace by labels require specify a labels set");
        }
        NonNamespaceOperation<Namespace, NamespaceList, Resource<Namespace>> namespaces
                = getEndpoint().getKubernetesClient().namespaces();
        for (Map.Entry<String, String> entry : labels.entrySet()) {
            namespaces.withLabel(entry.getKey(), entry.getValue());
        }
        NamespaceList namespace = namespaces.list();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(namespace.getItems());
    }

};