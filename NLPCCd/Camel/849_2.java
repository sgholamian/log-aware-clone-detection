//,temp,sample_5892.java,2,11,temp,sample_578.java,2,12
//,3
public class xxx {
protected void doCreateResourceQuota(Exchange exchange, String operation) throws Exception {
ResourceQuota rq = null;
String rqName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_RESOURCES_QUOTA_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
ResourceQuotaSpec rqSpec = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_RESOURCE_QUOTA_SPEC, ResourceQuotaSpec.class);
if (ObjectHelper.isEmpty(rqName)) {


log.info("create a specific resource quota require specify a resource quota name");
}
}

};