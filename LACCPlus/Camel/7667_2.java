//,temp,DigitalOceanDropletsProducer.java,375,379,temp,DigitalOceanDropletsProducer.java,369,373
//,2
public class xxx {
    private void shutdownDroplet(Exchange exchange) throws Exception {
        Action action = getEndpoint().getDigitalOceanClient().shutdownDroplet(dropletId);
        LOG.trace("Shutdown Droplet {} : [{}] ", dropletId, action);
        exchange.getMessage().setBody(action);
    }

};