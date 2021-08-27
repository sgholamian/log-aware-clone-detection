//,temp,sample_4594.java,2,13,temp,sample_4590.java,2,12
//,3
public class xxx {
protected void doDeleteConfigMap(Exchange exchange, String operation) throws Exception {
String configMapName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_CONFIGMAP_NAME, String.class);
String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(configMapName)) {
throw new IllegalArgumentException("Delete a specific config map require specify a config map name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("delete a specific config map require specify a namespace name");
}
}

};