//,temp,DigitalOceanImagesProducer.java,132,150,temp,DigitalOceanImagesProducer.java,118,130
//,3
public class xxx {
    private void updateImage(Exchange exchange) throws Exception {
        Integer imageId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);

        if (ObjectHelper.isEmpty(imageId)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");
        }

        String name = exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class);

        if (ObjectHelper.isEmpty(name)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.NAME + " must be specified");
        }
        Image image = new Image();
        image.setId(imageId);
        image.setName(name);
        image = getEndpoint().getDigitalOceanClient().updateImage(image);
        LOG.trace("Update Image {} [{}] ", imageId, image);
        exchange.getMessage().setBody(image);
    }

};