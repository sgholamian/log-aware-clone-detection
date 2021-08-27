//,temp,DigitalOceanDropletsProducer.java,405,409,temp,DigitalOceanDropletsProducer.java,393,397
//,2
public class xxx {
    private void rebootDroplet(Exchange exchange) throws Exception {
        Action action = getEndpoint().getDigitalOceanClient().rebootDroplet(dropletId);
        LOG.trace("Reboot Droplet {} : [{}] ", dropletId, action);
        exchange.getMessage().setBody(action);
    }

};