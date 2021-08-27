//,temp,DigitalOceanBlockStoragesProducer.java,274,284,temp,DigitalOceanBlockStoragesProducer.java,81,91
//,2
public class xxx {
    private void getVolumeActions(Exchange exchange) throws Exception {
        String volumeId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, String.class);

        if (ObjectHelper.isEmpty(volumeId)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");
        }

        Actions actions = getEndpoint().getDigitalOceanClient().getAvailableVolumeActions(volumeId);
        LOG.trace("Actions for Volume {} [{}] ", volumeId, actions.getActions());
        exchange.getMessage().setBody(actions.getActions());
    }

};