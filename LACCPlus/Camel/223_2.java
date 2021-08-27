//,temp,DigitalOceanDropletsProducer.java,190,196,temp,DigitalOceanDropletsProducer.java,174,180
//,2
public class xxx {
    private void getDropletActions(Exchange exchange) throws Exception {
        Actions actions = getEndpoint().getDigitalOceanClient().getAvailableDropletActions(dropletId, configuration.getPage(),
                configuration.getPerPage());
        LOG.trace("Actions for Droplet {} : page {} / {} per page [{}] ", dropletId, configuration.getPage(),
                configuration.getPerPage(), actions.getActions());
        exchange.getMessage().setBody(actions.getActions());
    }

};