//,temp,sample_4877.java,2,10,temp,sample_6643.java,2,13
//,3
public class xxx {
protected void doDeleteService(Exchange exchange, String operation) throws Exception {
String serviceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SERVICE_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(serviceName)) {
throw new IllegalArgumentException( "Delete a specific service require specify a service name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("delete a specific service require specify a namespace name");
}
}

};