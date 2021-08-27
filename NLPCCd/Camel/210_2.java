//,temp,sample_1350.java,2,9,temp,sample_581.java,2,10
//,3
public class xxx {
protected void doDeleteResourceQuota(Exchange exchange, String operation) throws Exception {
String rqName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_RESOURCES_QUOTA_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(rqName)) {


log.info("delete a specific resource quota require specify a resource quota name");
}
}

};