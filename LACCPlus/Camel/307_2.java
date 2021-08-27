//,temp,KubernetesSecretsProducer.java,116,132,temp,KubernetesServiceAccountsProducer.java,117,133
//,2
public class xxx {
    protected void doGetServiceAccount(Exchange exchange, String operation) throws Exception {
        ServiceAccount sa = null;
        String saName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_SERVICE_ACCOUNT_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(saName)) {
            LOG.error("Get a specific Service Account require specify a Service Account name");
            throw new IllegalArgumentException("Get a specific Service Account require specify a Service Account name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Get a specific Service Account require specify a namespace name");
            throw new IllegalArgumentException("Get a specific Service Account require specify a namespace name");
        }
        sa = getEndpoint().getKubernetesClient().serviceAccounts().inNamespace(namespaceName).withName(saName).get();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(sa);
    }

};