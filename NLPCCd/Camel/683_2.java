//,temp,sample_1349.java,2,9,temp,sample_4589.java,2,10
//,3
public class xxx {
protected void doGetConfigMap(Exchange exchange, String operation) throws Exception {
ConfigMap configMap = null;
String cfMapName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_CONFIGMAP_NAME, String.class);
if (ObjectHelper.isEmpty(cfMapName)) {


log.info("get a specific configmap require specify a configmap name");
}
}

};