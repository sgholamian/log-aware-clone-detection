//,temp,KubernetesSecretsProducer.java,116,132,temp,KubernetesServiceAccountsProducer.java,117,133
//,2
public class xxx {
    protected void doGetSecret(Exchange exchange, String operation) throws Exception {
        Secret secret = null;
        String secretName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_SECRET_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(secretName)) {
            LOG.error("Get a specific Secret require specify a Secret name");
            throw new IllegalArgumentException("Get a specific Secret require specify a Secret name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Get a specific Secret require specify a namespace name");
            throw new IllegalArgumentException("Get a specific Secret require specify a namespace name");
        }
        secret = getEndpoint().getKubernetesClient().secrets().inNamespace(namespaceName).withName(secretName).get();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(secret);
    }

};