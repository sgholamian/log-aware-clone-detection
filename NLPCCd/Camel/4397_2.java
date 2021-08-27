//,temp,sample_825.java,2,12,temp,sample_4326.java,2,15
//,3
public class xxx {
protected void doCreatePod(Exchange exchange, String operation) throws Exception {
Pod pod = null;
String podName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_POD_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
PodSpec podSpec = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_POD_SPEC, PodSpec.class);
if (ObjectHelper.isEmpty(podName)) {
throw new IllegalArgumentException( "Create a specific pod require specify a pod name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("create a specific pod require specify a namespace name");
}
}

};