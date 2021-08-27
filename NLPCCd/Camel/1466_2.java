//,temp,sample_6412.java,2,11,temp,sample_6413.java,2,11
//,3
public class xxx {
private void getFloatingIPActions(Exchange exchange) throws Exception {
String ipAddress = exchange.getIn().getHeader(DigitalOceanHeaders.FLOATING_IP_ADDRESS, String.class);
if (ObjectHelper.isEmpty(ipAddress)) {
throw new IllegalArgumentException(DigitalOceanHeaders.FLOATING_IP_ADDRESS + " must be specified");
}
Actions actions = getEndpoint().getDigitalOceanClient().getAvailableFloatingIPActions(ipAddress, configuration.getPage(), configuration.getPerPage());


log.info("actions for floatingip page per page");
}

};