//,temp,DigitalOceanTagsProducer.java,87,96,temp,DigitalOceanActionsProducer.java,52,61
//,2
public class xxx {
    private void getAction(Exchange exchange) throws Exception {
        Integer actionId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);

        if (ObjectHelper.isEmpty(actionId)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.ID + " must be specified");
        }
        Action action = getEndpoint().getDigitalOceanClient().getActionInfo(actionId);
        LOG.trace("Action [{}] ", action);
        exchange.getMessage().setBody(action);
    }

};