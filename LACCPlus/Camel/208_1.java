//,temp,DigitalOceanTagsProducer.java,87,96,temp,DigitalOceanActionsProducer.java,52,61
//,2
public class xxx {
    private void deleteTag(Exchange exchange) throws Exception {
        String name = exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class);

        if (ObjectHelper.isEmpty(name)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.NAME + " must be specified");
        }
        Delete delete = getEndpoint().getDigitalOceanClient().deleteTag(name);
        LOG.trace("Delete Tag [{}] ", delete);
        exchange.getMessage().setBody(delete);
    }

};