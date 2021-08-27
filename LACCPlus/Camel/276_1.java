//,temp,KubernetesServiceAccountsProducer.java,154,170,temp,KubernetesServicesProducer.java,165,180
//,2
public class xxx {
    protected void doDeleteServiceAccount(Exchange exchange, String operation) throws Exception {
        String saName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_SERVICE_ACCOUNT_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(saName)) {
            LOG.error("Delete a specific Service Account require specify a Service Account name");
            throw new IllegalArgumentException("Delete a specific Service Account require specify a Service Account name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Delete a specific Service Account require specify a namespace name");
            throw new IllegalArgumentException("Delete a specific Service Account require specify a namespace name");
        }
        boolean saDeleted
                = getEndpoint().getKubernetesClient().serviceAccounts().inNamespace(namespaceName).withName(saName).delete();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(saDeleted);
    }

};