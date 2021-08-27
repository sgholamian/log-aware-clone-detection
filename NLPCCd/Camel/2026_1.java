//,temp,sample_3395.java,2,11,temp,sample_2618.java,2,11
//,2
public class xxx {
private void deleteSnapshot(Exchange exchange) throws Exception {
String snapshotId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, String.class);
if (ObjectHelper.isEmpty(snapshotId)) {
throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");
}
Delete delete = getEndpoint().getDigitalOceanClient().deleteSnapshot(snapshotId);


log.info("delete snapshot");
}

};