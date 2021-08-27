//,temp,sample_4877.java,2,10,temp,sample_6643.java,2,13
//,3
public class xxx {
protected void doDeleteDeployment(Exchange exchange, String operation) {
String deploymentName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_DEPLOYMENT_NAME, String.class);
String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(deploymentName)) {


log.info("delete a specific deployment require specify a deployment name");
}
}

};