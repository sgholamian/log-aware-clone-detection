//,temp,DigitalOceanDropletsProducer.java,307,316,temp,DigitalOceanSnapshotsProducer.java,81,92
//,3
public class xxx {
    private void getSnapshot(Exchange exchange) throws Exception {
        String snapshotId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, String.class);

        if (ObjectHelper.isEmpty(snapshotId)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");
        }

        Snapshot snapshot = getEndpoint().getDigitalOceanClient().getSnaphotInfo(snapshotId);
        LOG.trace("Snapshot [{}] ", snapshot);
        exchange.getMessage().setBody(snapshot);

    }

};