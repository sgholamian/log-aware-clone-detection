//,temp,sample_4297.java,2,17,temp,sample_6857.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (NetUtils.isValidIp(destIpOrCidr)) {
if (eth1ip != null && eth1mask != null) {
inSameSubnet = NetUtils.sameSubnet(eth1ip, destIpOrCidr, eth1mask);
} else {
s_logger.warn("addRouteToInternalIp: unable to determine same subnet: _eth1ip=" + eth1ip + ", dest ip=" + destIpOrCidr + ", _eth1mask=" + eth1mask);
}
} else {
inSameSubnet = NetUtils.isNetworkAWithinNetworkB(destIpOrCidr, NetUtils.ipAndNetMaskToCidr(eth1ip, eth1mask));
}
if (inSameSubnet) {


log.info("addroutetointernalip dest ip is in the same subnet as ip");
}
}

};