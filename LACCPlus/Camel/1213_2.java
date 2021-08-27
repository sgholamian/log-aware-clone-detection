//,temp,DigitalOceanDropletsProducer.java,182,188,temp,DigitalOceanActionsProducer.java,63,69
//,3
public class xxx {
    private void getActions(Exchange exchange) throws Exception {
        Actions actions = getEndpoint().getDigitalOceanClient().getAvailableActions(configuration.getPage(),
                configuration.getPerPage());
        LOG.trace("All Actions : page {} / {} per page [{}] ", configuration.getPage(), configuration.getPerPage(),
                actions.getActions());
        exchange.getMessage().setBody(actions.getActions());
    }

};