//,temp,sample_5480.java,2,11,temp,sample_6410.java,2,11
//,3
public class xxx {
private void getVolumeSnapshots(Exchange exchange) throws Exception {
String volumeId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, String.class);
if (ObjectHelper.isEmpty(volumeId)) {
throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");
}
Snapshots snapshots = getEndpoint().getDigitalOceanClient().getVolumeSnapshots(volumeId, configuration.getPage(), configuration.getPerPage());


log.info("all snapshots for volume");
}

};