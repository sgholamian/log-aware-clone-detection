//,temp,DigitalOceanDropletsProducer.java,182,188,temp,DigitalOceanActionsProducer.java,63,69
//,3
public class xxx {
    private void getDropletKernels(Exchange exchange) throws Exception {
        Kernels kernels = getEndpoint().getDigitalOceanClient().getDropletKernels(dropletId, configuration.getPage(),
                configuration.getPerPage());
        LOG.trace("Kernels for Droplet {} : page {} / {} per page [{}] ", dropletId, configuration.getPage(),
                configuration.getPerPage(), kernels.getKernels());
        exchange.getMessage().setBody(kernels.getKernels());
    }

};