//,temp,KubernetesNamespacesProducer.java,137,147,temp,KubernetesServiceAccountsProducer.java,135,152
//,3
public class xxx {
    protected void doCreateServiceAccount(Exchange exchange, String operation) throws Exception {
        ServiceAccount sa = null;
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        ServiceAccount saToCreate
                = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_SERVICE_ACCOUNT, ServiceAccount.class);
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Create a specific Service Account require specify a namespace name");
            throw new IllegalArgumentException("Create a specific Service Account require specify a namespace name");
        }
        if (ObjectHelper.isEmpty(saToCreate)) {
            LOG.error("Create a specific Service Account require specify a Service Account bean");
            throw new IllegalArgumentException("Create a specific Service Account require specify a Service Account bean");
        }
        sa = getEndpoint().getKubernetesClient().serviceAccounts().inNamespace(namespaceName).create(saToCreate);

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(sa);
    }

};