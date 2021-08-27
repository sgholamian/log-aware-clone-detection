//,temp,sample_4314.java,2,12,temp,sample_4561.java,2,11
//,3
public class xxx {
public String getCanonicalHostName(String hostName) {
try {
return InetAddress.getByName(hostName).getCanonicalHostName();
}
catch(UnknownHostException exception) {


log.info("could not retrieve canonical hostname for");
}
}

};