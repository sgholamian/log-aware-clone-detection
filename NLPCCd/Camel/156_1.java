//,temp,sample_3209.java,2,7,temp,sample_3208.java,2,7
//,3
public class xxx {
private void getDropletNeighbors(Exchange exchange) throws Exception {
Droplets droplets = getEndpoint().getDigitalOceanClient().getDropletNeighbors(dropletId, configuration.getPage());


log.info("neighbors for droplet page");
}

};