//,temp,DigitalOceanTagsProducer.java,80,85,temp,DigitalOceanImagesProducer.java,75,81
//,2
public class xxx {
    private void getUserImages(Exchange exchange) throws Exception {
        Images images
                = getEndpoint().getDigitalOceanClient().getUserImages(configuration.getPage(), configuration.getPerPage());
        LOG.trace("User images : page {} / {} per page [{}] ", configuration.getPage(), configuration.getPerPage(),
                images.getImages());
        exchange.getMessage().setBody(images.getImages());
    }

};