//,temp,DigitalOceanFloatingIPsProducer.java,149,161,temp,DigitalOceanBlockStoragesProducer.java,154,164
//,3
public class xxx {
    private void getFloatingIPActions(Exchange exchange) throws Exception {
        String ipAddress = exchange.getIn().getHeader(DigitalOceanHeaders.FLOATING_IP_ADDRESS, String.class);

        if (ObjectHelper.isEmpty(ipAddress)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.FLOATING_IP_ADDRESS + " must be specified");
        }

        Actions actions = getEndpoint().getDigitalOceanClient().getAvailableFloatingIPActions(ipAddress,
                configuration.getPage(), configuration.getPerPage());
        LOG.trace("Actions for FloatingIP {} : page {} / {} per page [{}] ", ipAddress, configuration.getPage(),
                configuration.getPerPage(), actions.getActions());
        exchange.getMessage().setBody(actions.getActions());
    }

};