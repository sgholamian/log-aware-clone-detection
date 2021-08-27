//,temp,DigitalOceanBlockStoragesProducer.java,220,249,temp,DigitalOceanBlockStoragesProducer.java,127,152
//,3
public class xxx {
    private void getVolume(Exchange exchange) throws Exception {
        String volumeId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, String.class);
        Volume volume = null;
        if (ObjectHelper.isEmpty(volumeId)) {
            String name = exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class);
            String region = exchange.getIn().getHeader(DigitalOceanHeaders.REGION, String.class);

            if (ObjectHelper.isEmpty(name) && ObjectHelper.isEmpty(region)) {
                throw new IllegalArgumentException(
                        DigitalOceanHeaders.ID + " or " + DigitalOceanHeaders.NAME + " and " + DigitalOceanHeaders.REGION
                                                   + " must be specified");
            }

            List<Volume> volumes = getEndpoint().getDigitalOceanClient().getVolumeInfo(name, region).getVolumes();
            if (!volumes.isEmpty()) {
                // the volume is the first element in the list
                volume = volumes.get(0);
            }
        } else {
            volume = getEndpoint().getDigitalOceanClient().getVolumeInfo(volumeId);
        }

        LOG.trace("Volume [{}] ", volume);
        exchange.getMessage().setBody(volume);

    }

};