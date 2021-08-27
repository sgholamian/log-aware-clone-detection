//,temp,sample_5893.java,2,14,temp,sample_6639.java,2,12
//,3
public class xxx {
protected void doGetBuild(Exchange exchange, String operation) throws Exception {
Build build = null;
String buildName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_BUILD_NAME, String.class);
String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(buildName)) {
throw new IllegalArgumentException("Get a specific Build require specify a Build name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("get a specific build require specify a namespace name");
}
}

};