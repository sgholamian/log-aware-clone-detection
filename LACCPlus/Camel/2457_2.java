//,temp,KubernetesPodsProducer.java,115,131,temp,KubernetesDeploymentsProducer.java,112,123
//,3
public class xxx {
    protected void doGetDeployment(Exchange exchange, String operation) throws Exception {
        Deployment deployment = null;
        String deploymentName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_DEPLOYMENT_NAME, String.class);
        if (ObjectHelper.isEmpty(deploymentName)) {
            LOG.error("Get a specific Deployment require specify a Deployment name");
            throw new IllegalArgumentException("Get a specific Deployment require specify a Deployment name");
        }
        deployment = getEndpoint().getKubernetesClient().apps().deployments().withName(deploymentName).get();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(deployment);
    }

};