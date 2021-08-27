//,temp,sample_255.java,2,14,temp,sample_5639.java,2,10
//,3
public class xxx {
protected void doGetServiceAccount(Exchange exchange, String operation) throws Exception {
ServiceAccount sa = null;
String saName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SERVICE_ACCOUNT_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(saName)) {
throw new IllegalArgumentException( "Get a specific Service Account require specify a Service Account name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("get a specific service account require specify a namespace name");
}
}

};