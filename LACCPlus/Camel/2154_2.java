//,temp,KubernetesResourcesQuotaProducer.java,138,164,temp,KubernetesDeploymentsProducer.java,144,170
//,3
public class xxx {
    protected void doCreateDeployment(Exchange exchange, String operation) throws Exception {
        Deployment deployment = null;
        String deploymentName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_DEPLOYMENT_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        DeploymentSpec deSpec
                = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_DEPLOYMENT_SPEC, DeploymentSpec.class);
        if (ObjectHelper.isEmpty(deploymentName)) {
            LOG.error("Create a specific Deployment require specify a Deployment name");
            throw new IllegalArgumentException("Create a specific Deployment require specify a pod name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Create a specific Deployment require specify a namespace name");
            throw new IllegalArgumentException("Create a specific Deployment require specify a namespace name");
        }
        if (ObjectHelper.isEmpty(deSpec)) {
            LOG.error("Create a specific Deployment require specify a Deployment spec bean");
            throw new IllegalArgumentException("Create a specific Deployment require specify a Deployment spec bean");
        }
        Map<String, String> labels = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_DEPLOYMENTS_LABELS, Map.class);
        Deployment deploymentCreating = new DeploymentBuilder().withNewMetadata().withName(deploymentName).withLabels(labels)
                .endMetadata().withSpec(deSpec).build();
        deployment = getEndpoint().getKubernetesClient().apps().deployments().inNamespace(namespaceName)
                .create(deploymentCreating);

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(deployment);
    }

};