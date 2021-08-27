//,temp,sample_3206.java,2,7,temp,sample_1846.java,2,7
//,3
public class xxx {
private void getDropletKernels(Exchange exchange) throws Exception {
Kernels kernels = getEndpoint().getDigitalOceanClient().getDropletKernels(dropletId, configuration.getPage(), configuration.getPerPage());


log.info("kernels for droplet page per page");
}

};