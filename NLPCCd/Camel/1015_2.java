//,temp,sample_6409.java,2,11,temp,sample_4738.java,2,11
//,3
public class xxx {
private void getImageActions(Exchange exchange) throws Exception {
Integer imageId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);
if (ObjectHelper.isEmpty(imageId)) {
throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");
}
Actions actions = getEndpoint().getDigitalOceanClient().getAvailableImageActions(imageId, configuration.getPage(), configuration.getPerPage());


log.info("actions for image page per page");
}

};