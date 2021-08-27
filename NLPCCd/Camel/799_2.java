//,temp,sample_2535.java,2,10,temp,sample_7132.java,2,11
//,3
public class xxx {
public static String getEffectiveAddress(Exchange exchange, String defaultAddress) {
String retval = exchange.getIn().getHeader(Exchange.DESTINATION_OVERRIDE_URL, String.class);
if (retval == null) {
retval = defaultAddress;
} else {


log.info("client address is overridden by header to value");
}
}

};