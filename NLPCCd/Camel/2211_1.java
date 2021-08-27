//,temp,sample_6641.java,2,17,temp,sample_4327.java,2,17
//,2
public class xxx {
public void dummy_method(){
String serviceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SERVICE_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
ServiceSpec serviceSpec = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SERVICE_SPEC, ServiceSpec.class);
if (ObjectHelper.isEmpty(serviceName)) {
throw new IllegalArgumentException( "Create a specific service require specify a service name");
}
if (ObjectHelper.isEmpty(namespaceName)) {
throw new IllegalArgumentException( "Create a specific service require specify a namespace name");
}
if (ObjectHelper.isEmpty(serviceSpec)) {


log.info("create a specific service require specify a service spec bean");
}
}

};