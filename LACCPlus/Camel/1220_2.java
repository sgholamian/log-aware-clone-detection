//,temp,DigitalOceanDropletsProducer.java,198,204,temp,DigitalOceanKeysProducer.java,79,83
//,3
public class xxx {
    private void getKeys(Exchange exchange) throws Exception {
        Keys keys = getEndpoint().getDigitalOceanClient().getAvailableKeys(configuration.getPage());
        LOG.trace("All Keys : page {} [{}] ", configuration.getPage(), keys.getKeys());
        exchange.getMessage().setBody(keys.getKeys());
    }

};