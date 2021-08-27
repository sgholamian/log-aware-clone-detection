//,temp,DigitalOceanImagesProducer.java,164,180,temp,DigitalOceanImagesProducer.java,152,162
//,3
public class xxx {
    private void deleteImage(Exchange exchange) throws Exception {
        Integer imageId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);

        if (ObjectHelper.isEmpty(imageId)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");
        }

        Delete delete = getEndpoint().getDigitalOceanClient().deleteImage(imageId);
        LOG.trace("Delete  Image {} [{}] ", imageId, delete);
        exchange.getMessage().setBody(delete);
    }

};