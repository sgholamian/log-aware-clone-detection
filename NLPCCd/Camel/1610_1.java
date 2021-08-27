//,temp,sample_4741.java,2,15,temp,sample_4740.java,2,11
//,3
public class xxx {
private void transferImage(Exchange exchange) throws Exception {
Integer imageId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);
if (ObjectHelper.isEmpty(imageId)) {
throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");
}
String region = exchange.getIn().getHeader(DigitalOceanHeaders.REGION, String.class);
if (ObjectHelper.isEmpty(region)) {
throw new IllegalArgumentException(DigitalOceanHeaders.REGION + " must be specified");
}
Action action = getEndpoint().getDigitalOceanClient().transferImage(imageId, region);


log.info("transfer image to region");
}

};