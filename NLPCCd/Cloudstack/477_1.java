//,temp,sample_3352.java,2,9,temp,sample_3351.java,2,9
//,3
public class xxx {
public Network.Interface getBridgeByIp(String ip) throws Ovm3ResourceException {
if (getNetIface(ADDRESS, ip) != null && getNetIface(ADDRESS, ip).getIfType().contentEquals(BRIDGE)) {
return getNetIface(ADDRESS, ip);
}


log.info("unable to find bridge by ip");
}

};