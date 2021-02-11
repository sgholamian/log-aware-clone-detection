//,temp,sample_1128.java,2,12,temp,sample_1129.java,2,12
//,2
public class xxx {
public boolean stopVpn(final RemoteAccessVpn vpn) throws ResourceUnavailableException {
if (vpn.getVpcId() == null) {
return false;
}
final List<DomainRouterVO> routers = _vpcRouterMgr.getVpcRouters(vpn.getVpcId());
if (routers == null) {


log.info("cannot apply vpn users on the backend virtual router doesn t exist in the network");
}
}

};