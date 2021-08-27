//,temp,DigitalOceanDropletsProducer.java,295,305,temp,DigitalOceanSnapshotsProducer.java,94,105
//,3
public class xxx {
    private void deleteSnapshot(Exchange exchange) throws Exception {
        String snapshotId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, String.class);

        if (ObjectHelper.isEmpty(snapshotId)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");
        }

        Delete delete = getEndpoint().getDigitalOceanClient().deleteSnapshot(snapshotId);
        LOG.trace("Delete Snapshot [{}] ", delete);
        exchange.getMessage().setBody(delete);

    }

};