//,temp,DigitalOceanDropletsProducer.java,363,367,temp,DigitalOceanAccountProducer.java,33,38
//,3
public class xxx {
    private void powerOffDroplet(Exchange exchange) throws Exception {
        Action action = getEndpoint().getDigitalOceanClient().powerOffDroplet(dropletId);
        LOG.trace("Power off Droplet {} : [{}] ", dropletId, action);
        exchange.getMessage().setBody(action);
    }

};