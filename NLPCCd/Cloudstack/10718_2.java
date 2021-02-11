//,temp,sample_1364.java,2,11,temp,sample_1365.java,2,12
//,3
public class xxx {
private static String getRouterSshControlIp(NetworkElementCommand cmd) {
String routerIp = cmd.getAccessDetail(NetworkElementCommand.ROUTER_IP);
String routerGuestIp = cmd.getAccessDetail(NetworkElementCommand.ROUTER_GUEST_IP);
String zoneNetworkType = cmd.getAccessDetail(NetworkElementCommand.ZONE_NETWORK_TYPE);
if (routerGuestIp != null && zoneNetworkType != null && NetworkType.valueOf(zoneNetworkType) == NetworkType.Basic) {
return routerGuestIp;
}


log.info("use router s private ip for ssh control ip");
}

};