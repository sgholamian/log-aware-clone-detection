//,temp,sample_577.java,2,14,temp,sample_6640.java,2,15
//,3
public class xxx {
protected void doCreateService(Exchange exchange, String operation) throws Exception {
Service service = null;
String serviceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SERVICE_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
ServiceSpec serviceSpec = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SERVICE_SPEC, ServiceSpec.class);
if (ObjectHelper.isEmpty(serviceName)) {
throw new IllegalArgumentException( "Create a specific service require specify a service name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("create a specific service require specify a namespace name");
}
}

};