//,temp,sample_4594.java,2,13,temp,sample_4590.java,2,12
//,3
public class xxx {
protected void doCreateConfigMap(Exchange exchange, String operation) throws Exception {
ConfigMap configMap = null;
String cfMapName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_CONFIGMAP_NAME, String.class);
String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
HashMap<String, String> configMapData = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_CONFIGMAP_DATA, HashMap.class);
if (ObjectHelper.isEmpty(cfMapName)) {


log.info("create a specific configmap require specify a configmap name");
}
}

};