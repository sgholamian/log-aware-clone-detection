//,temp,KubernetesDeploymentsProducer.java,172,193,temp,KubernetesDeploymentsProducer.java,125,142
//,3
public class xxx {
    protected void doScaleDeployment(Exchange exchange, String operation) throws Exception {
        String deploymentName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_DEPLOYMENT_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        Integer replicasNumber = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_DEPLOYMENT_REPLICAS, Integer.class);
        if (ObjectHelper.isEmpty(deploymentName)) {
            LOG.error("Scale a specific deployment require specify a deployment name");
            throw new IllegalArgumentException("Scale a specific deployment require specify a deployment name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Scale a specific deployment require specify a namespace name");
            throw new IllegalArgumentException("Scale a specific deployment require specify a namespace name");
        }
        if (ObjectHelper.isEmpty(replicasNumber)) {
            LOG.error("Scale a specific deployment require specify a replicas number");
            throw new IllegalArgumentException("Scale a specific deployment require specify a replicas number");
        }
        Deployment deploymentScaled = getEndpoint().getKubernetesClient().apps().deployments().inNamespace(namespaceName)
                .withName(deploymentName).scale(replicasNumber, false);

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(deploymentScaled.getStatus().getReplicas());
    }

};