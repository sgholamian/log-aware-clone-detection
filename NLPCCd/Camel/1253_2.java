//,temp,sample_4880.java,2,15,temp,sample_4879.java,2,12
//,3
public class xxx {
protected void doCreateDeployment(Exchange exchange, String operation) throws Exception {
Deployment deployment = null;
String deploymentName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_DEPLOYMENT_NAME, String.class);
String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
DeploymentSpec deSpec = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_DEPLOYMENT_SPEC, DeploymentSpec.class);
if (ObjectHelper.isEmpty(deploymentName)) {


log.info("create a specific deployment require specify a deployment name");
}
}

};