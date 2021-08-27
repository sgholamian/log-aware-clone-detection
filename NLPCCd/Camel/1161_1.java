//,temp,sample_577.java,2,14,temp,sample_6640.java,2,15
//,3
public class xxx {
protected void doGetResourceQuota(Exchange exchange, String operation) throws Exception {
ResourceQuota rq = null;
String rqName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_RESOURCES_QUOTA_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(rqName)) {
throw new IllegalArgumentException( "Get a specific Resource Quota require specify a Resource Quota name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("get a specific resource quota require specify a namespace name");
}
}

};