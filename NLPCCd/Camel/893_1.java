//,temp,sample_4592.java,2,17,temp,sample_4329.java,2,13
//,3
public class xxx {
public void dummy_method(){
String cfMapName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_CONFIGMAP_NAME, String.class);
String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
HashMap<String, String> configMapData = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_CONFIGMAP_DATA, HashMap.class);
if (ObjectHelper.isEmpty(cfMapName)) {
throw new IllegalArgumentException("Create a specific configMap require specify a configMap name");
}
if (ObjectHelper.isEmpty(namespaceName)) {
throw new IllegalArgumentException("Create a specific configMap require specify a namespace name");
}
if (ObjectHelper.isEmpty(configMapData)) {


log.info("create a specific configmap require specify a data map");
}
}

};