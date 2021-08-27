//,temp,KubernetesCustomResourcesProducer.java,150,169,temp,KubernetesCustomResourcesProducer.java,128,148
//,3
public class xxx {
    protected void doGet(Exchange exchange, String operation, String namespaceName) throws Exception {
        String customResourceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_CRD_INSTANCE_NAME, String.class);
        if (ObjectHelper.isEmpty(customResourceName)) {
            throw new IllegalArgumentException("Get a specific Deployment require specify a Deployment name");
        }
        JsonObject customResourceJSON = new JsonObject();
        try {
            customResourceJSON = new JsonObject(
                    getEndpoint().getKubernetesClient().customResource(getCRDContext(exchange.getIn())).get(namespaceName,
                            customResourceName));
        } catch (KubernetesClientException e) {
            if (e.getCode() == 404) {
                LOG.info("Custom resource instance not found", e);
            } else {
                throw e;
            }
        }

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(customResourceJSON);
    }

};