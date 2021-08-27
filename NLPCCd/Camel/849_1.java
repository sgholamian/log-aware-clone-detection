//,temp,sample_5892.java,2,11,temp,sample_578.java,2,12
//,3
public class xxx {
protected void doGetBuild(Exchange exchange, String operation) throws Exception {
Build build = null;
String buildName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_BUILD_NAME, String.class);
String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(buildName)) {


log.info("get a specific build require specify a build name");
}
}

};