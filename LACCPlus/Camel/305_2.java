//,temp,KubernetesSecretsProducer.java,152,168,temp,KubernetesConfigMapsProducer.java,152,168
//,2
public class xxx {
    protected void doDeleteConfigMap(Exchange exchange, String operation) throws Exception {
        String configMapName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_CONFIGMAP_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(configMapName)) {
            LOG.error("Delete a specific config map require specify a config map name");
            throw new IllegalArgumentException("Delete a specific config map require specify a config map name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Delete a specific config map require specify a namespace name");
            throw new IllegalArgumentException("Delete a specific config map require specify a namespace name");
        }
        boolean cfMapDeleted
                = getEndpoint().getKubernetesClient().configMaps().inNamespace(namespaceName).withName(configMapName).delete();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(cfMapDeleted);
    }

};