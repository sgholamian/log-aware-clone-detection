//,temp,sample_3951.java,2,11,temp,sample_1046.java,2,14
//,3
public class xxx {
protected void doGetBuildConfig(Exchange exchange, String operation) throws Exception {
BuildConfig buildConfig = null;
String buildConfigName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_BUILD_CONFIG_NAME, String.class);
String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(buildConfigName)) {
throw new IllegalArgumentException("Get a specific Build Config require specify a Build Config name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("get a specific build config require specify a namespace name");
}
}

};