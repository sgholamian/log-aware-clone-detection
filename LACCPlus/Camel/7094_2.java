//,temp,DigitalOceanSnapshotsProducer.java,59,79,temp,DigitalOceanImagesProducer.java,83,97
//,3
public class xxx {
    private void getImages(Exchange exchange) throws Exception {
        DigitalOceanImageTypes type = exchange.getIn().getHeader(DigitalOceanHeaders.TYPE, DigitalOceanImageTypes.class);
        Images images;

        if (ObjectHelper.isNotEmpty(type)) {
            images = getEndpoint().getDigitalOceanClient().getAvailableImages(configuration.getPage(),
                    configuration.getPerPage(), ActionType.valueOf(type.name()));
        } else {
            images = getEndpoint().getDigitalOceanClient().getAvailableImages(configuration.getPage(),
                    configuration.getPerPage());
        }
        LOG.trace("All Images : page {} / {} per page [{}] ", configuration.getPage(), configuration.getPerPage(),
                images.getImages());
        exchange.getMessage().setBody(images.getImages());
    }

};