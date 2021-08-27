//,temp,DigitalOceanTagsProducer.java,69,78,temp,DigitalOceanFloatingIPsProducer.java,95,105
//,2
public class xxx {
    private void getFloatingIP(Exchange exchange) throws Exception {
        String ipAddress = exchange.getIn().getHeader(DigitalOceanHeaders.FLOATING_IP_ADDRESS, String.class);

        if (ObjectHelper.isEmpty(ipAddress)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.FLOATING_IP_ADDRESS + " must be specified");
        }

        FloatingIP ip = getEndpoint().getDigitalOceanClient().getFloatingIPInfo(ipAddress);
        LOG.trace("Floating IP {}", ip);
        exchange.getMessage().setBody(ip);
    }

};