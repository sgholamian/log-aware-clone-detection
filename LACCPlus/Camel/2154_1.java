//,temp,KubernetesResourcesQuotaProducer.java,138,164,temp,KubernetesDeploymentsProducer.java,144,170
//,3
public class xxx {
    protected void doCreateResourceQuota(Exchange exchange, String operation) throws Exception {
        ResourceQuota rq = null;
        String rqName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_RESOURCES_QUOTA_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        ResourceQuotaSpec rqSpec
                = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_RESOURCE_QUOTA_SPEC, ResourceQuotaSpec.class);
        if (ObjectHelper.isEmpty(rqName)) {
            LOG.error("Create a specific resource quota require specify a resource quota name");
            throw new IllegalArgumentException("Create a specific resource quota require specify a resource quota name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Create a specific resource quota require specify a namespace name");
            throw new IllegalArgumentException("Create a specific resource quota require specify a namespace name");
        }
        if (ObjectHelper.isEmpty(rqSpec)) {
            LOG.error("Create a specific resource quota require specify a resource quota spec bean");
            throw new IllegalArgumentException("Create a specific resource quota require specify a resource quota spec bean");
        }
        Map<String, String> labels
                = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_RESOURCES_QUOTA_LABELS, Map.class);
        ResourceQuota rqCreating = new ResourceQuotaBuilder().withNewMetadata().withName(rqName).withLabels(labels)
                .endMetadata().withSpec(rqSpec).build();
        rq = getEndpoint().getKubernetesClient().resourceQuotas().inNamespace(namespaceName).create(rqCreating);

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(rq);
    }

};