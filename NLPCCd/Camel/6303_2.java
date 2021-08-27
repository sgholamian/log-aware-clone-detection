//,temp,sample_3393.java,2,16,temp,sample_4736.java,2,13
//,3
public class xxx {
private void getImages(Exchange exchange) throws Exception {
DigitalOceanImageTypes type = exchange.getIn().getHeader(DigitalOceanHeaders.TYPE, DigitalOceanImageTypes.class);
Images images;
if (ObjectHelper.isNotEmpty(type)) {
images = getEndpoint().getDigitalOceanClient().getAvailableImages(configuration.getPage(), configuration.getPerPage(), ActionType.valueOf(type.name()));
} else {
images = getEndpoint().getDigitalOceanClient().getAvailableImages(configuration.getPage(), configuration.getPerPage());
}


log.info("all images page per page");
}

};