//,temp,DigitalOceanFloatingIPsProducer.java,119,135,temp,DigitalOceanFloatingIPsProducer.java,69,85
//,3
public class xxx {
    private void createFloatingIp(Exchange exchange) throws Exception {
        Integer dropletId = exchange.getIn().getHeader(DigitalOceanHeaders.DROPLET_ID, Integer.class);
        String region = exchange.getIn().getHeader(DigitalOceanHeaders.REGION, String.class);
        FloatingIP ip;

        if (ObjectHelper.isNotEmpty(dropletId)) {
            ip = getEndpoint().getDigitalOceanClient().createFloatingIP(dropletId);
        } else if (ObjectHelper.isNotEmpty(region)) {
            ip = getEndpoint().getDigitalOceanClient().createFloatingIP(region);
        } else {
            throw new IllegalArgumentException(
                    DigitalOceanHeaders.DROPLET_ID + " or " + DigitalOceanHeaders.REGION + " must be specified");
        }

        LOG.trace("FloatingIP [{}] ", ip);
        exchange.getMessage().setBody(ip);
    }

};