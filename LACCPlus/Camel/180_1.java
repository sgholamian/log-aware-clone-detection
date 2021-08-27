//,temp,DigitalOceanFloatingIPsProducer.java,137,147,temp,DigitalOceanFloatingIPsProducer.java,107,117
//,2
public class xxx {
    private void unassignFloatingIP(Exchange exchange) throws Exception {
        String ipAddress = exchange.getIn().getHeader(DigitalOceanHeaders.FLOATING_IP_ADDRESS, String.class);

        if (ObjectHelper.isEmpty(ipAddress)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.FLOATING_IP_ADDRESS + " must be specified");
        }

        Action action = getEndpoint().getDigitalOceanClient().unassignFloatingIP(ipAddress);
        LOG.trace("Unassign Floating IP {}", action);
        exchange.getMessage().setBody(action);
    }

};