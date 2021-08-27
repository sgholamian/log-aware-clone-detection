//,temp,DigitalOceanFloatingIPsProducer.java,119,135,temp,DigitalOceanFloatingIPsProducer.java,69,85
//,3
public class xxx {
    private void assignFloatingIPToDroplet(Exchange exchange) throws Exception {
        Integer dropletId = exchange.getIn().getHeader(DigitalOceanHeaders.DROPLET_ID, Integer.class);

        if (ObjectHelper.isEmpty(dropletId)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.DROPLET_ID + " must be specified");
        }

        String ipAddress = exchange.getIn().getHeader(DigitalOceanHeaders.FLOATING_IP_ADDRESS, String.class);

        if (ObjectHelper.isEmpty(ipAddress)) {
            throw new IllegalArgumentException(DigitalOceanHeaders.FLOATING_IP_ADDRESS + " must be specified");
        }

        Action action = getEndpoint().getDigitalOceanClient().assignFloatingIP(dropletId, ipAddress);
        LOG.trace("Assign Floating IP to Droplet {}", action);
        exchange.getMessage().setBody(action);
    }

};