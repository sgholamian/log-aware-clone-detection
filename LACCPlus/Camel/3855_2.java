//,temp,DigitalOceanDropletsProducer.java,425,435,temp,DigitalOceanTagsProducer.java,58,67
//,3
public class xxx {
    private void createTag(Exchange exchange) throws Exception {
        String name = exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class);

        if (ObjectHelper.isEmpty(name)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.NAME + " must be specified");
        }
        Tag tag = getEndpoint().getDigitalOceanClient().createTag(name);
        LOG.trace("Create Tag [{}] ", tag);
        exchange.getMessage().setBody(tag);
    }

};