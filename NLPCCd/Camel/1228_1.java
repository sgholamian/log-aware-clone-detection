//,temp,sample_4323.java,2,11,temp,sample_259.java,2,13
//,3
public class xxx {
protected void doGetPod(Exchange exchange, String operation) throws Exception {
Pod pod = null;
String podName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_POD_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(podName)) {


log.info("get a specific pod require specify a pod name");
}
}

};