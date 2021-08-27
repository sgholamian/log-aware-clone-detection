//,temp,DigitalOceanBlockStoragesProducer.java,220,249,temp,DigitalOceanBlockStoragesProducer.java,127,152
//,3
public class xxx {
    private void detachVolumeToDroplet(Exchange exchange) throws Exception {
        String volumeId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, String.class);
        String volumeName = exchange.getIn().getHeader(DigitalOceanHeaders.VOLUME_NAME, String.class);
        Integer dropletId = exchange.getIn().getHeader(DigitalOceanHeaders.DROPLET_ID, Integer.class);
        String region = exchange.getIn().getHeader(DigitalOceanHeaders.REGION, String.class);

        if (ObjectHelper.isEmpty(dropletId)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.DROPLET_ID + " must be specified");
        }

        if (ObjectHelper.isEmpty(region)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.REGION + " must be specified");
        }

        Action action;

        if (ObjectHelper.isNotEmpty(volumeName)) {
            action = getEndpoint().getDigitalOceanClient().detachVolumeByName(dropletId, volumeName, region);
            LOG.trace("Detach Volume {} to Droplet {} [{}] ", volumeName, dropletId, action);
        } else if (ObjectHelper.isNotEmpty(volumeId)) {
            action = getEndpoint().getDigitalOceanClient().detachVolume(dropletId, volumeId, region);
            LOG.trace("Detach Volume {} to Droplet {} [{}] ", volumeId, dropletId, action);
        } else {
            throw new IllegalArgumentException(
                    DigitalOceanHeaders.ID + " or " + DigitalOceanHeaders.VOLUME_NAME + " must be specified");
        }

        exchange.getMessage().setBody(action);

    }

};