//,temp,sample_1045.java,2,11,temp,sample_4593.java,2,10
//,3
public class xxx {
protected void doGetBuildConfig(Exchange exchange, String operation) throws Exception {
BuildConfig buildConfig = null;
String buildConfigName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_BUILD_CONFIG_NAME, String.class);
String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(buildConfigName)) {


log.info("get a specific build config require specify a build config name");
}
}

};