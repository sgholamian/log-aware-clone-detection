//,temp,sample_1465.java,2,13,temp,sample_2476.java,2,13
//,3
public class xxx {
public Set<Long> getAvailableIps(Network network, String requestedIp) {
String[] cidr = network.getCidr().split("/");
List<String> ips = getUsedIpsInNetwork(network);
Set<Long> usedIps = new TreeSet<Long>();
for (String ip : ips) {
if (requestedIp != null && requestedIp.equals(ip)) {


log.info("requested ip address is already in use in network");
}
}
}

};