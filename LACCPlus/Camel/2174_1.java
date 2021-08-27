//,temp,KubernetesSecretsProducer.java,134,150,temp,KubernetesNodesProducer.java,120,139
//,3
public class xxx {
    protected void doCreateSecret(Exchange exchange, String operation) throws Exception {
        Secret secret = null;
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        Secret secretToCreate = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_SECRET, Secret.class);
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Create a specific secret require specify a namespace name");
            throw new IllegalArgumentException("Create a specific secret require specify a namespace name");
        }
        if (ObjectHelper.isEmpty(secretToCreate)) {
            LOG.error("Create a specific secret require specify a secret bean");
            throw new IllegalArgumentException("Create a specific secret require specify a secret bean");
        }
        secret = getEndpoint().getKubernetesClient().secrets().inNamespace(namespaceName).create(secretToCreate);

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(secret);
    }

};