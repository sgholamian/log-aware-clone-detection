//,temp,KubernetesResourcesQuotaProducer.java,166,182,temp,KubernetesReplicationControllersProducer.java,183,200
//,2
public class xxx {
    protected void doDeleteResourceQuota(Exchange exchange, String operation) throws Exception {
        String rqName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_RESOURCES_QUOTA_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(rqName)) {
            LOG.error("Delete a specific resource quota require specify a resource quota name");
            throw new IllegalArgumentException("Delete a specific resource quota require specify a resource quota name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Delete a specific resource quota require specify a namespace name");
            throw new IllegalArgumentException("Delete a specific resource quota require specify a namespace name");
        }
        boolean rqDeleted
                = getEndpoint().getKubernetesClient().resourceQuotas().inNamespace(namespaceName).withName(rqName).delete();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(rqDeleted);
    }

};