//,temp,sample_1895.java,2,11,temp,sample_1657.java,2,12
//,3
public class xxx {
private static String resolveLocalHostIPAddress() {
String address;
try {
address = InetAddress.getLocalHost().getHostAddress();
} catch (UnknownHostException e) {


log.info("unable to determine address of the host falling back to localhost address");
}
}

};