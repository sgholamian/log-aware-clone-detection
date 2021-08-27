//,temp,DigitalOceanDropletsProducer.java,399,403,temp,DigitalOceanDropletsProducer.java,387,391
//,2
public class xxx {
    private void disableDropletBackups(Exchange exchange) throws Exception {
        Action action = getEndpoint().getDigitalOceanClient().disableDropletBackups(dropletId);
        LOG.trace("Disable backups for Droplet {} : [{}] ", dropletId, action);
        exchange.getMessage().setBody(action);
    }

};