//,temp,sample_579.java,2,15,temp,sample_6638.java,2,14
//,3
public class xxx {
protected void doGetService(Exchange exchange, String operation) throws Exception {
Service service = null;
String serviceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SERVICE_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(serviceName)) {
throw new IllegalArgumentException( "Get a specific service require specify a service name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("get a specific service require specify a namespace name");
}
}

};