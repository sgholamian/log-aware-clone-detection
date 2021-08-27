//,temp,DigitalOceanDropletsProducer.java,166,172,temp,DigitalOceanFloatingIPsProducer.java,87,93
//,2
public class xxx {
    private void getFloatingIPs(Exchange exchange) throws Exception {
        FloatingIPs ips = getEndpoint().getDigitalOceanClient().getAvailableFloatingIPs(configuration.getPage(),
                configuration.getPerPage());
        LOG.trace("All Floating IPs : page {} / {} per page [{}] ", configuration.getPage(), configuration.getPerPage(),
                ips.getFloatingIPs());
        exchange.getMessage().setBody(ips.getFloatingIPs());
    }

};