//,temp,sample_6409.java,2,11,temp,sample_4738.java,2,11
//,3
public class xxx {
private void getFloatingIP(Exchange exchange) throws Exception {
String ipAddress = exchange.getIn().getHeader(DigitalOceanHeaders.FLOATING_IP_ADDRESS, String.class);
if (ObjectHelper.isEmpty(ipAddress)) {
throw new IllegalArgumentException(DigitalOceanHeaders.FLOATING_IP_ADDRESS + " must be specified");
}
FloatingIP ip = getEndpoint().getDigitalOceanClient().getFloatingIPInfo(ipAddress);


log.info("floating ip");
}

};