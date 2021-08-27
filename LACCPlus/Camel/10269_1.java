//,temp,DigitalOceanDropletsProducer.java,166,172,temp,DigitalOceanFloatingIPsProducer.java,87,93
//,2
public class xxx {
    private void getDroplets(Exchange exchange) throws Exception {
        Droplets droplets = getEndpoint().getDigitalOceanClient().getAvailableDroplets(configuration.getPage(),
                configuration.getPerPage());
        LOG.trace("All Droplets : page {} / {} per page [{}] ", configuration.getPage(), configuration.getPerPage(),
                droplets.getDroplets());
        exchange.getMessage().setBody(droplets.getDroplets());
    }

};