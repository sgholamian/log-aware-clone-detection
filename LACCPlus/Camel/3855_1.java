//,temp,DigitalOceanDropletsProducer.java,425,435,temp,DigitalOceanTagsProducer.java,58,67
//,3
public class xxx {
    private void tagDroplet(Exchange exchange) throws Exception {
        if (ObjectHelper.isEmpty(exchange.getIn().getHeader(DigitalOceanHeaders.NAME))) {
            throw new IllegalArgumentException(DigitalOceanHeaders.NAME + " must be specified");
        }

        ArrayList<Resource> resources = new ArrayList<>(1);
        resources.add(new Resource(dropletId.toString(), ResourceType.DROPLET));
        Response response = getEndpoint().getDigitalOceanClient().tagResources(dropletId.toString(), resources);
        LOG.trace("Tag Droplet {} : [{}] ", dropletId, response);
        exchange.getMessage().setBody(response);
    }

};