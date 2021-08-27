//,temp,KubernetesJobProducer.java,129,153,temp,KubernetesConfigMapsProducer.java,125,150
//,3
public class xxx {
    protected void doCreateConfigMap(Exchange exchange, String operation) throws Exception {
        ConfigMap configMap = null;
        String cfMapName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_CONFIGMAP_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        HashMap<String, String> configMapData
                = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_CONFIGMAP_DATA, HashMap.class);
        if (ObjectHelper.isEmpty(cfMapName)) {
            LOG.error("Create a specific configMap require specify a configMap name");
            throw new IllegalArgumentException("Create a specific configMap require specify a configMap name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Create a specific configMap require specify a namespace name");
            throw new IllegalArgumentException("Create a specific configMap require specify a namespace name");
        }
        if (ObjectHelper.isEmpty(configMapData)) {
            LOG.error("Create a specific configMap require specify a data map");
            throw new IllegalArgumentException("Create a specific configMap require specify a data map");
        }
        Map<String, String> labels = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_CONFIGMAPS_LABELS, Map.class);
        ConfigMap cfMapCreating = new ConfigMapBuilder().withNewMetadata().withName(cfMapName).withLabels(labels).endMetadata()
                .withData(configMapData).build();
        configMap = getEndpoint().getKubernetesClient().configMaps().inNamespace(namespaceName).create(cfMapCreating);

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(configMap);
    }

};