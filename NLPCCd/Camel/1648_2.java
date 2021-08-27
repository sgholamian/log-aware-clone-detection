//,temp,sample_5638.java,2,14,temp,sample_6637.java,2,11
//,3
public class xxx {
protected void doGetService(Exchange exchange, String operation) throws Exception {
Service service = null;
String serviceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SERVICE_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(serviceName)) {


log.info("get a specific service require specify a service name");
}
}

};