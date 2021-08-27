//,temp,DigitalOceanDropletsProducer.java,411,423,temp,DigitalOceanImagesProducer.java,99,116
//,3
public class xxx {
    private void takeDropletSnapshot(Exchange exchange) throws Exception {
        Action action;

        if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(DigitalOceanHeaders.NAME))) {
            action = getEndpoint().getDigitalOceanClient().takeDropletSnapshot(dropletId,
                    exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class));
        } else {
            action = getEndpoint().getDigitalOceanClient().takeDropletSnapshot(dropletId);
        }

        LOG.trace("Take Snapshot for Droplet {} : [{}] ", dropletId, action);
        exchange.getMessage().setBody(action);
    }

};