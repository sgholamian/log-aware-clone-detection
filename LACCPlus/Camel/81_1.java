//,temp,DigitalOceanDropletsProducer.java,399,403,temp,DigitalOceanDropletsProducer.java,387,391
//,2
public class xxx {
    private void enableDropletPrivateNetworking(Exchange exchange) throws Exception {
        Action action = getEndpoint().getDigitalOceanClient().enableDropletPrivateNetworking(dropletId);
        LOG.trace("Enable private networking for Droplet {} : [{}] ", dropletId, action);
        exchange.getMessage().setBody(action);
    }

};