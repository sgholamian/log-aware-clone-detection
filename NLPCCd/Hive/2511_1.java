//,temp,sample_4314.java,2,12,temp,sample_4561.java,2,11
//,3
public class xxx {
private static String normalizeHostname(String name) {
try {
InetAddress address = InetAddress.getByName( "localhost".equalsIgnoreCase(name) ? null : name);
return address.getCanonicalHostName();
}
catch (UnknownHostException ex) {


log.info("unable to normalize hostname");
}
}

};