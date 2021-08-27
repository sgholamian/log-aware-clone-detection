//,temp,DigitalOceanDropletsProducer.java,295,305,temp,DigitalOceanSnapshotsProducer.java,94,105
//,3
public class xxx {
    private void restoreDroplet(Exchange exchange) throws Exception {
        if (ObjectHelper.isEmpty(exchange.getIn().getHeader(DigitalOceanHeaders.IMAGE_ID))) {
            throw new IllegalArgumentException(DigitalOceanHeaders.IMAGE_ID + " must be specified");
        }

        Action action = getEndpoint().getDigitalOceanClient().restoreDroplet(dropletId,
                exchange.getIn().getHeader(DigitalOceanHeaders.IMAGE_ID, Integer.class));
        LOG.trace("DropletAction Restore [{}] ", action);
        exchange.getMessage().setBody(action);

    }

};