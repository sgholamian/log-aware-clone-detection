//,temp,DigitalOceanDropletsProducer.java,357,361,temp,DigitalOceanDropletsProducer.java,218,222
//,3
public class xxx {
    private void deleteDroplet(Exchange exchange) throws Exception {
        Delete delete = getEndpoint().getDigitalOceanClient().deleteDroplet(dropletId);
        LOG.trace("Delete Droplet {}", delete);
        exchange.getMessage().setBody(delete);
    }

};