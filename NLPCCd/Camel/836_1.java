//,temp,sample_4328.java,2,10,temp,sample_4324.java,2,14
//,3
public class xxx {
protected void doDeletePod(Exchange exchange, String operation) throws Exception {
String podName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_POD_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(podName)) {


log.info("delete a specific pod require specify a pod name");
}
}

};