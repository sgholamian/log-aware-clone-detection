//,temp,sample_3609.java,2,7,temp,sample_3204.java,2,7
//,3
public class xxx {
private void getDroplets(Exchange exchange) throws Exception {
Droplets droplets = getEndpoint().getDigitalOceanClient().getAvailableDroplets(configuration.getPage(), configuration.getPerPage());


log.info("all droplets page per page");
}

};