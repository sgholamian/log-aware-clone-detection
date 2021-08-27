//,temp,sample_1351.java,2,9,temp,sample_6642.java,2,10
//,3
public class xxx {
protected void doDeleteService(Exchange exchange, String operation) throws Exception {
String serviceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SERVICE_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(serviceName)) {


log.info("delete a specific service require specify a service name");
}
}

};