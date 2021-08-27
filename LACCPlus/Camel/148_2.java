//,temp,DigitalOceanBlockStoragesProducer.java,274,284,temp,DigitalOceanBlockStoragesProducer.java,81,91
//,2
public class xxx {
    private void getVolumes(Exchange exchange) throws Exception {
        String region = exchange.getIn().getHeader(DigitalOceanHeaders.REGION, String.class);
        if (ObjectHelper.isEmpty(region)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.REGION + " must be specified");
        }

        Volumes volumes = getEndpoint().getDigitalOceanClient().getAvailableVolumes(region);
        LOG.trace("All Volumes for region {} [{}] ", region, volumes.getVolumes());
        exchange.getMessage().setBody(volumes.getVolumes());

    }

};