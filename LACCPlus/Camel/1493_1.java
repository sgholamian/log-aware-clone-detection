//,temp,KubernetesNamespacesProducer.java,110,120,temp,KubernetesJobProducer.java,111,127
//,3
public class xxx {
    protected void doGetNamespace(Exchange exchange, String operation) {
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Get a specific namespace require specify a namespace name");
            throw new IllegalArgumentException("Get a specific namespace require specify a namespace name");
        }
        Namespace namespace = getEndpoint().getKubernetesClient().namespaces().withName(namespaceName).get();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(namespace);
    }

};