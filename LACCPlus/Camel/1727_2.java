//,temp,KubernetesHPAProducer.java,114,131,temp,KubernetesConfigMapsProducer.java,107,123
//,3
public class xxx {
    protected void doGetConfigMap(Exchange exchange, String operation) throws Exception {
        ConfigMap configMap = null;
        String cfMapName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_CONFIGMAP_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(cfMapName)) {
            LOG.error("Get a specific ConfigMap require specify a ConfigMap name");
            throw new IllegalArgumentException("Get a specific ConfigMap require specify a ConfigMap name");
        }
        if (namespaceName != null) {
            configMap = getEndpoint().getKubernetesClient().configMaps().inNamespace(namespaceName).withName(cfMapName).get();
        } else {
            configMap = getEndpoint().getKubernetesClient().configMaps().withName(cfMapName).get();
        }

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(configMap);
    }

};