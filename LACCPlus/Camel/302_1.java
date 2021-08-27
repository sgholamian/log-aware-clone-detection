//,temp,KubernetesResourcesQuotaProducer.java,120,136,temp,KubernetesReplicationControllersProducer.java,134,151
//,2
public class xxx {
    protected void doGetResourceQuota(Exchange exchange, String operation) throws Exception {
        ResourceQuota rq = null;
        String rqName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_RESOURCES_QUOTA_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(rqName)) {
            LOG.error("Get a specific Resource Quota require specify a Resource Quota name");
            throw new IllegalArgumentException("Get a specific Resource Quota require specify a Resource Quota name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Get a specific Resource Quota require specify a namespace name");
            throw new IllegalArgumentException("Get a specific Resource Quota require specify a namespace name");
        }
        rq = getEndpoint().getKubernetesClient().resourceQuotas().inNamespace(namespaceName).withName(rqName).get();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(rq);
    }

};