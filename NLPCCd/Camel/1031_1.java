//,temp,sample_582.java,2,13,temp,sample_826.java,2,15
//,3
public class xxx {
protected void doDeleteResourceQuota(Exchange exchange, String operation) throws Exception {
String rqName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_RESOURCES_QUOTA_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(rqName)) {
throw new IllegalArgumentException( "Delete a specific resource quota require specify a resource quota name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("delete a specific resource quota require specify a namespace name");
}
}

};