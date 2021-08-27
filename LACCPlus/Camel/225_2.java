//,temp,DigitalOceanDropletsProducer.java,340,349,temp,DigitalOceanDropletsProducer.java,329,338
//,2
public class xxx {
    private void renameDroplet(Exchange exchange) throws Exception {
        if (ObjectHelper.isEmpty(exchange.getIn().getHeader(DigitalOceanHeaders.NAME))) {
            throw new IllegalArgumentException(DigitalOceanHeaders.NAME + " must be specified");
        }

        Action action = getEndpoint().getDigitalOceanClient().renameDroplet(dropletId,
                exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class));
        LOG.trace("Rename Droplet {} : [{}] ", dropletId, action);
        exchange.getMessage().setBody(action);
    }

};