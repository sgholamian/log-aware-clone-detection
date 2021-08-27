//,temp,sample_5477.java,2,11,temp,sample_1847.java,2,11
//,2
public class xxx {
private void getVolumes(Exchange exchange) throws Exception {
String region = exchange.getIn().getHeader(DigitalOceanHeaders.REGION, String.class);
if (ObjectHelper.isEmpty(region)) {
throw new IllegalArgumentException(DigitalOceanHeaders.REGION + " must be specified");
}
Volumes volumes = getEndpoint().getDigitalOceanClient().getAvailableVolumes(region);


log.info("all volumes for region");
}

};