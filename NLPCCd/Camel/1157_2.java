//,temp,sample_824.java,2,14,temp,sample_4325.java,2,12
//,3
public class xxx {
protected void doCreatePod(Exchange exchange, String operation) throws Exception {
Pod pod = null;
String podName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_POD_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
PodSpec podSpec = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_POD_SPEC, PodSpec.class);
if (ObjectHelper.isEmpty(podName)) {


log.info("create a specific pod require specify a pod name");
}
}

};