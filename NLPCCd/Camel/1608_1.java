//,temp,sample_4878.java,2,13,temp,sample_3954.java,2,15
//,3
public class xxx {
protected void doDeleteDeployment(Exchange exchange, String operation) {
String deploymentName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_DEPLOYMENT_NAME, String.class);
String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(deploymentName)) {
throw new IllegalArgumentException("Delete a specific deployment require specify a deployment name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("delete a specific deployment require specify a namespace name");
}
}

};