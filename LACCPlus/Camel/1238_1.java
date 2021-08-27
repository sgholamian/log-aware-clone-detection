//,temp,DigitalOceanDropletsProducer.java,318,327,temp,DigitalOceanImagesProducer.java,182,192
//,3
public class xxx {
    private void rebuildDroplet(Exchange exchange) throws Exception {
        if (ObjectHelper.isEmpty(exchange.getIn().getHeader(DigitalOceanHeaders.IMAGE_ID))) {
            throw new IllegalArgumentException(DigitalOceanHeaders.IMAGE_ID + " must be specified");
        }

        Action action = getEndpoint().getDigitalOceanClient().rebuildDroplet(dropletId,
                exchange.getIn().getHeader(DigitalOceanHeaders.IMAGE_ID, Integer.class));
        LOG.trace("Rebuild Droplet {} : [{}] ", dropletId, action);
        exchange.getMessage().setBody(action);
    }

};