//,temp,DigitalOceanDropletsProducer.java,351,355,temp,DigitalOceanDropletsProducer.java,160,164
//,3
public class xxx {
    private void getDroplet(Exchange exchange) throws Exception {
        Droplet droplet = getEndpoint().getDigitalOceanClient().getDropletInfo(dropletId);
        LOG.trace("Droplet {}", droplet);
        exchange.getMessage().setBody(droplet);
    }

};