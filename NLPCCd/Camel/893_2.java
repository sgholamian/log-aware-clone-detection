//,temp,sample_4592.java,2,17,temp,sample_4329.java,2,13
//,3
public class xxx {
protected void doDeletePod(Exchange exchange, String operation) throws Exception {
String podName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_POD_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(podName)) {
throw new IllegalArgumentException( "Delete a specific pod require specify a pod name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("delete a specific pod require specify a namespace name");
}
}

};