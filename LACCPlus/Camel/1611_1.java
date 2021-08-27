//,temp,KubernetesCustomResourcesProducer.java,150,169,temp,KubernetesCustomResourcesProducer.java,128,148
//,3
public class xxx {
    protected void doDelete(Exchange exchange, String operation, String namespaceName) throws Exception {
        String customResourceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_CRD_INSTANCE_NAME, String.class);
        if (ObjectHelper.isEmpty(customResourceName)) {
            LOG.error("Delete a specific deployment require specify a deployment name");
            throw new IllegalArgumentException("Delete a specific deployment require specify a deployment name");
        }

        try {
            RawCustomResourceOperationsImpl raw
                    = getEndpoint().getKubernetesClient().customResource(getCRDContext(exchange.getIn()));
            boolean deleted = raw.delete(namespaceName, customResourceName);
            exchange.getMessage().setHeader(KubernetesConstants.KUBERNETES_DELETE_RESULT, deleted);
        } catch (KubernetesClientException e) {
            if (e.getCode() == 404) {
                LOG.info("Custom resource instance not found", e);
            } else {
                throw e;
            }
        }
    }

};