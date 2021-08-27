//,temp,DigitalOceanDropletsProducer.java,405,409,temp,DigitalOceanDropletsProducer.java,393,397
//,2
public class xxx {
    private void enableDropletIpv6(Exchange exchange) throws Exception {
        Action action = getEndpoint().getDigitalOceanClient().enableDropletIpv6(dropletId);
        LOG.trace("Enable IP v6 for Droplet {} : [{}] ", dropletId, action);
        exchange.getMessage().setBody(action);
    }

};