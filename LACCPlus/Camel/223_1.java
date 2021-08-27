//,temp,DigitalOceanDropletsProducer.java,190,196,temp,DigitalOceanDropletsProducer.java,174,180
//,2
public class xxx {
    private void getDropletBackups(Exchange exchange) throws Exception {
        Backups backups = getEndpoint().getDigitalOceanClient().getDropletBackups(dropletId, configuration.getPage(),
                configuration.getPerPage());
        LOG.trace("Backups for Droplet {} : page {} / {} per page [{}] ", dropletId, configuration.getPage(),
                configuration.getPerPage(), backups.getBackups());
        exchange.getMessage().setBody(backups.getBackups());
    }

};