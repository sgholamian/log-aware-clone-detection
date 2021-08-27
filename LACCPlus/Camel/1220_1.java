//,temp,DigitalOceanDropletsProducer.java,198,204,temp,DigitalOceanKeysProducer.java,79,83
//,3
public class xxx {
    private void getDropletSnapshots(Exchange exchange) throws Exception {
        Snapshots snapshots = getEndpoint().getDigitalOceanClient().getDropletSnapshots(dropletId, configuration.getPage(),
                configuration.getPerPage());
        LOG.trace("Snapshots for Droplet {} : page {} / {} per page [{}] ", dropletId, configuration.getPage(),
                configuration.getPerPage(), snapshots.getSnapshots());
        exchange.getMessage().setBody(snapshots.getSnapshots());
    }

};