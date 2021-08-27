//,temp,sample_5893.java,2,14,temp,sample_6639.java,2,12
//,3
public class xxx {
protected void doCreateService(Exchange exchange, String operation) throws Exception {
Service service = null;
String serviceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SERVICE_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
ServiceSpec serviceSpec = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SERVICE_SPEC, ServiceSpec.class);
if (ObjectHelper.isEmpty(serviceName)) {


log.info("create a specific service require specify a service name");
}
}

};