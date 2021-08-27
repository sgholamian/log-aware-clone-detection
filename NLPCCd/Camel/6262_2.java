//,temp,sample_5482.java,2,17,temp,sample_6411.java,2,15
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


log.info("assign floating ip to droplet");
}

};