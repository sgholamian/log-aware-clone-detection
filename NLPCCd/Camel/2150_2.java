//,temp,sample_1844.java,2,11,temp,sample_5487.java,2,11
//,2
public class xxx {
private void getVolumeActions(Exchange exchange) throws Exception {
String volumeId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, String.class);
if (ObjectHelper.isEmpty(volumeId)) {
throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");
}
Actions actions = getEndpoint().getDigitalOceanClient().getAvailableVolumeActions(volumeId);


log.info("actions for volume");
}

};