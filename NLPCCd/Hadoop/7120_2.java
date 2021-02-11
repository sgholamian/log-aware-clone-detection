//,temp,sample_1895.java,2,11,temp,sample_1657.java,2,12
//,3
public class xxx {
private static String getHostName() {
String hostName = null;
try {
hostName = InetAddress.getLocalHost().getHostName();
}
catch (UnknownHostException ex) {


log.info("unable to obtain hostname");
}
}

};