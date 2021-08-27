//,temp,sample_5636.java,2,14,temp,sample_256.java,2,11
//,3
public class xxx {
protected void doCreateServiceAccount(Exchange exchange, String operation) throws Exception {
ServiceAccount sa = null;
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
ServiceAccount saToCreate = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SERVICE_ACCOUNT, ServiceAccount.class);
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("create a specific service account require specify a namespace name");
}
}

};