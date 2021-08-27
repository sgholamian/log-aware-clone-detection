//,temp,sample_4741.java,2,15,temp,sample_4740.java,2,11
//,3
public class xxx {
private void deleteImage(Exchange exchange) throws Exception {
Integer imageId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);
if (ObjectHelper.isEmpty(imageId)) {
throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");
}
Delete delete = getEndpoint().getDigitalOceanClient().deleteImage(imageId);


log.info("delete image");
}

};