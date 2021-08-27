//,temp,sample_6641.java,2,17,temp,sample_4327.java,2,17
//,2
public class xxx {
public void dummy_method(){
String podName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_POD_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
PodSpec podSpec = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_POD_SPEC, PodSpec.class);
if (ObjectHelper.isEmpty(podName)) {
throw new IllegalArgumentException( "Create a specific pod require specify a pod name");
}
if (ObjectHelper.isEmpty(namespaceName)) {
throw new IllegalArgumentException( "Create a specific pod require specify a namespace name");
}
if (ObjectHelper.isEmpty(podSpec)) {


log.info("create a specific pod require specify a pod spec bean");
}
}

};