//,temp,sample_1895.java,2,11,temp,sample_3713.java,2,11
//,3
public class xxx {
private String getHostname() {
String hostname = "Unknown";
try {
hostname = InetAddress.getLocalHost().getHostName();
} catch (Exception e) {


log.info("cannot get local address");
}
}

};