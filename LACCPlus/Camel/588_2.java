//,temp,DigitalOceanDropletsProducer.java,212,216,temp,DigitalOceanDropletsProducer.java,206,210
//,3
public class xxx {
    private void getDropletNeighbors(Exchange exchange) throws Exception {
        Droplets droplets = getEndpoint().getDigitalOceanClient().getDropletNeighbors(dropletId, configuration.getPage());
        LOG.trace("Neighbors for Droplet {} : page {} [{}] ", dropletId, configuration.getPage(), droplets.getDroplets());
        exchange.getMessage().setBody(droplets.getDroplets());
    }

};