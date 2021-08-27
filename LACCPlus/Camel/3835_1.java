//,temp,DigitalOceanDropletsProducer.java,307,316,temp,DigitalOceanSnapshotsProducer.java,81,92
//,3
public class xxx {
    private void resizeDroplet(Exchange exchange) throws Exception {
        if (ObjectHelper.isEmpty(exchange.getIn().getHeader(DigitalOceanHeaders.DROPLET_SIZE))) {
            throw new IllegalArgumentException(DigitalOceanHeaders.DROPLET_SIZE + " must be specified");
        }

        Action action = getEndpoint().getDigitalOceanClient().resizeDroplet(dropletId,
                exchange.getIn().getHeader(DigitalOceanHeaders.DROPLET_SIZE, String.class));
        LOG.trace("DropletAction Resize [{}] ", action);
        exchange.getMessage().setBody(action);
    }

};