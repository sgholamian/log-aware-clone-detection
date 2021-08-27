//,temp,DigitalOceanDropletsProducer.java,375,379,temp,DigitalOceanDropletsProducer.java,369,373
//,2
public class xxx {
    private void powerCycleDroplet(Exchange exchange) throws Exception {
        Action action = getEndpoint().getDigitalOceanClient().powerCycleDroplet(dropletId);
        LOG.trace("Power cycle Droplet {} : [{}] ", dropletId, action);
        exchange.getMessage().setBody(action);
    }

};