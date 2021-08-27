//,temp,sample_1845.java,2,11,temp,sample_4742.java,2,11
//,2
public class xxx {
private void convertImageToSnapshot(Exchange exchange) throws Exception {
Integer imageId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);
if (ObjectHelper.isEmpty(imageId)) {
throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");
}
Action action = getEndpoint().getDigitalOceanClient().convertImage(imageId);


log.info("convert image");
}

};