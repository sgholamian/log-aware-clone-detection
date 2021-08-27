//,temp,KubernetesSecretsProducer.java,152,168,temp,KubernetesConfigMapsProducer.java,152,168
//,2
public class xxx {
    protected void doDeleteSecret(Exchange exchange, String operation) throws Exception {
        String secretName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_SECRET_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(secretName)) {
            LOG.error("Delete a specific secret require specify a secret name");
            throw new IllegalArgumentException("Delete a specific secret require specify a secret name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Delete a specific secret require specify a namespace name");
            throw new IllegalArgumentException("Delete a specific secret require specify a namespace name");
        }
        boolean secretDeleted
                = getEndpoint().getKubernetesClient().secrets().inNamespace(namespaceName).withName(secretName).delete();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(secretDeleted);
    }

};