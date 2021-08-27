//,temp,sample_1045.java,2,11,temp,sample_4593.java,2,10
//,3
public class xxx {
protected void doDeleteConfigMap(Exchange exchange, String operation) throws Exception {
String configMapName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_CONFIGMAP_NAME, String.class);
String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(configMapName)) {


log.info("delete a specific config map require specify a config map name");
}
}

};