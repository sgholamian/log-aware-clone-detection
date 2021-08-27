//,temp,DigitalOceanFloatingIPsProducer.java,149,161,temp,DigitalOceanBlockStoragesProducer.java,154,164
//,3
public class xxx {
    private void getVolumeSnapshots(Exchange exchange) throws Exception {
        String volumeId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, String.class);
        if (ObjectHelper.isEmpty(volumeId)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");
        }

        Snapshots snapshots = getEndpoint().getDigitalOceanClient().getVolumeSnapshots(volumeId, configuration.getPage(),
                configuration.getPerPage());
        LOG.trace("All Snapshots for volume {} [{}] ", volumeId, snapshots.getSnapshots());
        exchange.getMessage().setBody(snapshots.getSnapshots());
    }

};