//,temp,sample_3960.java,2,17,temp,sample_580.java,2,17
//,2
public class xxx {
public void dummy_method(){
String rqName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_RESOURCES_QUOTA_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
ResourceQuotaSpec rqSpec = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_RESOURCE_QUOTA_SPEC, ResourceQuotaSpec.class);
if (ObjectHelper.isEmpty(rqName)) {
throw new IllegalArgumentException( "Create a specific resource quota require specify a resource quota name");
}
if (ObjectHelper.isEmpty(namespaceName)) {
throw new IllegalArgumentException( "Create a specific resource quota require specify a namespace name");
}
if (ObjectHelper.isEmpty(rqSpec)) {


log.info("create a specific resource quota require specify a resource quota spec bean");
}
}

};