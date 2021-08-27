//,temp,sample_4322.java,2,9,temp,sample_4876.java,2,10
//,3
public class xxx {
protected void doGetDeployment(Exchange exchange, String operation) throws Exception {
Deployment deployment = null;
String deploymentName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_DEPLOYMENT_NAME, String.class);
if (ObjectHelper.isEmpty(deploymentName)) {


log.info("get a specific deployment require specify a deployment name");
}
}

};