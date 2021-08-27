//,temp,sample_4737.java,2,16,temp,sample_263.java,2,16
//,2
public class xxx {
public void dummy_method(){
Integer imageId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);
String slug = exchange.getIn().getHeader(DigitalOceanHeaders.DROPLET_IMAGE, String.class);
Image image;
if (ObjectHelper.isNotEmpty(imageId)) {
image = getEndpoint().getDigitalOceanClient().getImageInfo(imageId);
} else if (ObjectHelper.isNotEmpty(slug)) {
image = getEndpoint().getDigitalOceanClient().getImageInfo(slug);
} else {
throw new IllegalArgumentException(DigitalOceanHeaders.ID + " or " + DigitalOceanHeaders.DROPLET_IMAGE + " must be specified");
}


log.info("image");
}

};