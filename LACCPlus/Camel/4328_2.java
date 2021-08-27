//,temp,DigitalOceanImagesProducer.java,132,150,temp,DigitalOceanImagesProducer.java,118,130
//,3
public class xxx {
    private void getImageActions(Exchange exchange) throws Exception {
        Integer imageId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);

        if (ObjectHelper.isEmpty(imageId)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");
        }

        Actions actions = getEndpoint().getDigitalOceanClient().getAvailableImageActions(imageId, configuration.getPage(),
                configuration.getPerPage());
        LOG.trace("Actions for Image {} : page {} / {} per page [{}] ", imageId, configuration.getPage(),
                configuration.getPerPage(), actions.getActions());
        exchange.getMessage().setBody(actions.getActions());
    }

};