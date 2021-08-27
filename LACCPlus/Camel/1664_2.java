//,temp,KubernetesDeploymentsProducer.java,172,193,temp,KubernetesDeploymentsProducer.java,125,142
//,3
public class xxx {
    protected void doDeleteDeployment(Exchange exchange, String operation) {
        String deploymentName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_DEPLOYMENT_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(deploymentName)) {
            LOG.error("Delete a specific deployment require specify a deployment name");
            throw new IllegalArgumentException("Delete a specific deployment require specify a deployment name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Delete a specific deployment require specify a namespace name");
            throw new IllegalArgumentException("Delete a specific deployment require specify a namespace name");
        }

        Boolean deployment = getEndpoint().getKubernetesClient().apps().deployments().inNamespace(namespaceName)
                .withName(deploymentName).delete();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(deployment);
    }

};