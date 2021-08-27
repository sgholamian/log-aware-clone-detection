//,temp,sample_4328.java,2,10,temp,sample_4324.java,2,14
//,3
public class xxx {
protected void doGetPod(Exchange exchange, String operation) throws Exception {
Pod pod = null;
String podName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_POD_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(podName)) {
throw new IllegalArgumentException( "Get a specific pod require specify a pod name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("get a specific pod require specify a namespace name");
}
}

};