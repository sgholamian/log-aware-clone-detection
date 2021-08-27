//,temp,sample_4323.java,2,11,temp,sample_259.java,2,13
//,3
public class xxx {
protected void doDeleteServiceAccount(Exchange exchange, String operation) throws Exception {
String saName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SERVICE_ACCOUNT_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(saName)) {
throw new IllegalArgumentException( "Delete a specific Service Account require specify a Service Account name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("delete a specific service account require specify a namespace name");
}
}

};