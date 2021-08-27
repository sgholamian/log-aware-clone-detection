//,temp,sample_576.java,2,11,temp,sample_829.java,2,13
//,3
public class xxx {
protected void doGetResourceQuota(Exchange exchange, String operation) throws Exception {
ResourceQuota rq = null;
String rqName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_RESOURCES_QUOTA_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(rqName)) {


log.info("get a specific resource quota require specify a resource quota name");
}
}

};