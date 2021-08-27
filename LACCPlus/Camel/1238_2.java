//,temp,DigitalOceanDropletsProducer.java,318,327,temp,DigitalOceanImagesProducer.java,182,192
//,3
public class xxx {
    private void convertImageToSnapshot(Exchange exchange) throws Exception {
        Integer imageId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);

        if (ObjectHelper.isEmpty(imageId)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");
        }

        Action action = getEndpoint().getDigitalOceanClient().convertImage(imageId);
        LOG.trace("Convert Image {} [{}] ", imageId, action);
        exchange.getMessage().setBody(action);
    }

};