//,temp,DigitalOceanFloatingIPsProducer.java,137,147,temp,DigitalOceanFloatingIPsProducer.java,107,117
//,2
public class xxx {
    private void deleteFloatingIP(Exchange exchange) throws Exception {
        String ipAddress = exchange.getIn().getHeader(DigitalOceanHeaders.FLOATING_IP_ADDRESS, String.class);

        if (ObjectHelper.isEmpty(ipAddress)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.FLOATING_IP_ADDRESS + " must be specified");
        }

        Delete delete = getEndpoint().getDigitalOceanClient().deleteFloatingIP(ipAddress);
        LOG.trace("Delete Floating IP {}", delete);
        exchange.getMessage().setBody(delete);
    }

};