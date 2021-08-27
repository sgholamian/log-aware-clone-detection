//,temp,DigitalOceanDropletsProducer.java,437,447,temp,DigitalOceanKeysProducer.java,103,127
//,3
public class xxx {
    private void untagDroplet(Exchange exchange) throws Exception {
        if (ObjectHelper.isEmpty(exchange.getIn().getHeader(DigitalOceanHeaders.NAME))) {
            throw new IllegalArgumentException(DigitalOceanHeaders.NAME + " must be specified");
        }

        ArrayList<Resource> resources = new ArrayList<>(1);
        resources.add(new Resource(dropletId.toString(), ResourceType.DROPLET));
        Response response = getEndpoint().getDigitalOceanClient().untagResources(dropletId.toString(), resources);
        LOG.trace("Untag Droplet {} : [{}] ", dropletId, response);
        exchange.getMessage().setBody(response);
    }

};