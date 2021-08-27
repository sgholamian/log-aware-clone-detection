//,temp,sample_258.java,2,10,temp,sample_254.java,2,11
//,3
public class xxx {
protected void doGetServiceAccount(Exchange exchange, String operation) throws Exception {
ServiceAccount sa = null;
String saName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_SERVICE_ACCOUNT_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(saName)) {


log.info("get a specific service account require specify a service account name");
}
}

};