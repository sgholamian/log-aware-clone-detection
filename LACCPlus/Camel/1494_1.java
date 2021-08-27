//,temp,KubernetesNamespacesProducer.java,137,147,temp,KubernetesServiceAccountsProducer.java,135,152
//,3
public class xxx {
    protected void doDeleteNamespace(Exchange exchange, String operation) {
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Delete a specific namespace require specify a namespace name");
            throw new IllegalArgumentException("Delete a specific namespace require specify a namespace name");
        }
        Boolean namespace = getEndpoint().getKubernetesClient().namespaces().withName(namespaceName).delete();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(namespace);
    }

};