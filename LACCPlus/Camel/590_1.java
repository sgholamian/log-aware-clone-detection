//,temp,DigitalOceanDropletsProducer.java,351,355,temp,DigitalOceanDropletsProducer.java,160,164
//,3
public class xxx {
    private void resetDropletPassword(Exchange exchange) throws Exception {
        Action action = getEndpoint().getDigitalOceanClient().resetDropletPassword(dropletId);
        LOG.trace("Reset password Droplet {} : [{}] ", dropletId, action);
        exchange.getMessage().setBody(action);
    }

};