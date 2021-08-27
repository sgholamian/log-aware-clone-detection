//,temp,KubernetesCustomResourcesProducer.java,171,187,temp,KubernetesCustomResourcesProducer.java,96,112
//,3
public class xxx {
    protected void doCreate(Exchange exchange, String operation, String namespaceName) throws Exception {
        String customResourceInstance = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_CRD_INSTANCE, String.class);
        JsonObject gitHubSourceJSON = new JsonObject();
        try {
            gitHubSourceJSON = new JsonObject(
                    getEndpoint().getKubernetesClient().customResource(getCRDContext(exchange.getIn())).create(namespaceName,
                            customResourceInstance));
        } catch (KubernetesClientException e) {
            if (e.getCode() == 409) {
                LOG.info("Custom resource instance already exists", e);
            } else {
                throw e;
            }
        }
        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(gitHubSourceJSON);
    }

};