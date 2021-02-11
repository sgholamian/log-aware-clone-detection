//,temp,sample_5201.java,2,18,temp,sample_5121.java,2,18
//,3
public class xxx {
public void dummy_method(){
final URI broadcastUri = BroadcastDomainType.fromString(ip.getBroadcastUri());
if (BroadcastDomainType.getSchemeValue(broadcastUri) != BroadcastDomainType.Vlan) {
throw new InternalErrorException("Unable to assign a public IP to a VIF on network " + ip.getBroadcastUri());
}
final String vlanId = BroadcastDomainType.getValue(broadcastUri);
int publicNicInfo = -1;
publicNicInfo = getVmNics(routerName, vlanId);
boolean addVif = false;
if (ip.isAdd() && publicNicInfo == -1) {
if (s_logger.isDebugEnabled()) {


log.info("plug new nic to associate to");
}
}
}

};