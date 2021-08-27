//,temp,sample_2619.java,2,7,temp,sample_6408.java,2,7
//,2
public class xxx {
private void getFloatingIPs(Exchange exchange) throws Exception {
FloatingIPs ips = getEndpoint().getDigitalOceanClient().getAvailableFloatingIPs(configuration.getPage(), configuration.getPerPage());


log.info("all floating ips page per page");
}

};