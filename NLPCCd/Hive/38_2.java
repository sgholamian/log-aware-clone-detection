//,temp,sample_1115.java,2,10,temp,sample_629.java,2,10
//,2
public class xxx {
public static String hostname() {
try {
return InetAddress.getLocalHost().getHostName();
} catch (UnknownHostException e) {


log.info("unable to resolve my host name");
}
}

};