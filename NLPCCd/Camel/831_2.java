//,temp,sample_5480.java,2,11,temp,sample_6410.java,2,11
//,3
public class xxx {
private void deleteFloatingIP(Exchange exchange) throws Exception {
String ipAddress = exchange.getIn().getHeader(DigitalOceanHeaders.FLOATING_IP_ADDRESS, String.class);
if (ObjectHelper.isEmpty(ipAddress)) {
throw new IllegalArgumentException(DigitalOceanHeaders.FLOATING_IP_ADDRESS + " must be specified");
}
Delete delete = getEndpoint().getDigitalOceanClient().deleteFloatingIP(ipAddress);


log.info("delete floating ip");
}

};