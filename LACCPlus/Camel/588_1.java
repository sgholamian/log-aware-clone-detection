//,temp,DigitalOceanDropletsProducer.java,212,216,temp,DigitalOceanDropletsProducer.java,206,210
//,3
public class xxx {
    private void getAllDropletNeighbors(Exchange exchange) throws Exception {
        Neighbors neighbors = getEndpoint().getDigitalOceanClient().getAllDropletNeighbors(configuration.getPage());
        LOG.trace("All Neighbors : page {} [{}] ", configuration.getPage(), neighbors.getNeighbors());
        exchange.getMessage().setBody(neighbors.getNeighbors());
    }

};