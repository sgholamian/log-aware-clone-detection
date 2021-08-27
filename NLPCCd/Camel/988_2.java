//,temp,sample_5637.java,2,11,temp,sample_257.java,2,14
//,3
public class xxx {
protected void doCreateServiceAccount(Exchange exchange, String operation) throws Exception {
ServiceAccount sa = null;
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
ServiceAccount saToCreate = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SERVICE_ACCOUNT, ServiceAccount.class);
if (ObjectHelper.isEmpty(namespaceName)) {
throw new IllegalArgumentException( "Create a specific Service Account require specify a namespace name");
}
if (ObjectHelper.isEmpty(saToCreate)) {


log.info("create a specific service account require specify a service account bean");
}
}

};