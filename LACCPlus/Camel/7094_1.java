//,temp,DigitalOceanSnapshotsProducer.java,59,79,temp,DigitalOceanImagesProducer.java,83,97
//,3
public class xxx {
    private void getSnapshots(Exchange exchange) throws Exception {
        DigitalOceanSnapshotTypes type = exchange.getIn().getHeader(DigitalOceanHeaders.TYPE, DigitalOceanSnapshotTypes.class);
        Snapshots snapshots;

        if (ObjectHelper.isNotEmpty(type)) {
            if (type == DigitalOceanSnapshotTypes.droplet) {
                snapshots = getEndpoint().getDigitalOceanClient().getAllDropletSnapshots(configuration.getPage(),
                        configuration.getPerPage());
            } else {
                snapshots = getEndpoint().getDigitalOceanClient().getAllVolumeSnapshots(configuration.getPage(),
                        configuration.getPerPage());
            }
        } else {
            snapshots = getEndpoint().getDigitalOceanClient().getAvailableSnapshots(configuration.getPage(),
                    configuration.getPerPage());
        }

        LOG.trace("All Snapshots : page {} / {} per page [{}] ", configuration.getPage(), configuration.getPerPage(),
                snapshots.getSnapshots());
        exchange.getMessage().setBody(snapshots.getSnapshots());
    }

};