//,temp,DigitalOceanDropletsProducer.java,411,423,temp,DigitalOceanImagesProducer.java,99,116
//,3
public class xxx {
    private void getImage(Exchange exchange) throws Exception {

        Integer imageId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);
        String slug = exchange.getIn().getHeader(DigitalOceanHeaders.DROPLET_IMAGE, String.class);
        Image image;

        if (ObjectHelper.isNotEmpty(imageId)) {
            image = getEndpoint().getDigitalOceanClient().getImageInfo(imageId);
        } else if (ObjectHelper.isNotEmpty(slug)) {
            image = getEndpoint().getDigitalOceanClient().getImageInfo(slug);
        } else {
            throw new IllegalArgumentException(
                    DigitalOceanHeaders.ID + " or " + DigitalOceanHeaders.DROPLET_IMAGE + " must be specified");
        }

        LOG.trace("Image [{}] ", image);
        exchange.getMessage().setBody(image);
    }

};