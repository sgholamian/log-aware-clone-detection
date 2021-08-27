//,temp,DigitalOceanTagsProducer.java,80,85,temp,DigitalOceanImagesProducer.java,75,81
//,2
public class xxx {
    private void getTags(Exchange exchange) throws Exception {
        Tags tags = getEndpoint().getDigitalOceanClient().getAvailableTags(configuration.getPage(), configuration.getPerPage());
        LOG.trace("All Tags : page {} / {} per page [{}] ", configuration.getPage(), configuration.getPerPage(),
                tags.getTags());
        exchange.getMessage().setBody(tags.getTags());
    }

};