//,temp,DigitalOceanBlockStoragesProducer.java,166,188,temp,DigitalOceanKeysProducer.java,129,151
//,3
public class xxx {
    private void deleteVolume(Exchange exchange) throws Exception {
        String volumeId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, String.class);
        Delete delete;
        if (ObjectHelper.isEmpty(volumeId)) {
            String name = exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class);
            String region = exchange.getIn().getHeader(DigitalOceanHeaders.REGION, String.class);

            if (ObjectHelper.isEmpty(name) && ObjectHelper.isEmpty(region)) {
                throw new IllegalArgumentException(
                        DigitalOceanHeaders.ID + " or " + DigitalOceanHeaders.NAME + " and " + DigitalOceanHeaders.REGION
                                                   + " must be specified");
            }

            delete = getEndpoint().getDigitalOceanClient().deleteVolume(name, region);

        } else {
            delete = getEndpoint().getDigitalOceanClient().deleteVolume(volumeId);
        }

        LOG.trace("Delete Volume [{}] ", delete);
        exchange.getMessage().setBody(delete);

    }

};