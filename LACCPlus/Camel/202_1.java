//,temp,DigitalOceanTagsProducer.java,69,78,temp,DigitalOceanFloatingIPsProducer.java,95,105
//,2
public class xxx {
    private void getTag(Exchange exchange) throws Exception {
        String name = exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class);

        if (ObjectHelper.isEmpty(name)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.NAME + " must be specified");
        }
        Tag tag = getEndpoint().getDigitalOceanClient().getTag(name);
        LOG.trace("Tag [{}] ", tag);
        exchange.getMessage().setBody(tag);
    }

};