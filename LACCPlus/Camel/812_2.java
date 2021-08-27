//,temp,KubernetesCustomResourcesProducer.java,171,187,temp,KubernetesCustomResourcesProducer.java,96,112
//,3
public class xxx {
    protected void doList(Exchange exchange, String operation, String namespaceName) throws Exception {
        JsonObject customResourcesListJSON = new JsonObject(
                getEndpoint().getKubernetesClient().customResource(getCRDContext(exchange.getIn())).list(namespaceName));
        if (LOG.isDebugEnabled()) {
            LOG.debug(customResourcesListJSON.toString());
        }

        JsonArray customResourcesListItems;
        if (customResourcesListJSON.getCollection("items") != null) {
            customResourcesListItems = new JsonArray(customResourcesListJSON.getCollection("items"));
        } else {
            customResourcesListItems = new JsonArray();
        }

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(customResourcesListItems);
    }

};