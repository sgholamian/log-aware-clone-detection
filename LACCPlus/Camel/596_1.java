//,temp,DigitalOceanDropletsProducer.java,357,361,temp,DigitalOceanDropletsProducer.java,218,222
//,3
public class xxx {
    private void powerOnDroplet(Exchange exchange) throws Exception {
        Action action = getEndpoint().getDigitalOceanClient().powerOnDroplet(dropletId);
        LOG.trace("Power on Droplet {} : [{}] ", dropletId, action);
        exchange.getMessage().setBody(action);
    }

};