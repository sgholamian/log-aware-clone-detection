//,temp,sample_1126.java,2,11,temp,sample_1125.java,2,11
//,2
public class xxx {
public boolean startSite2SiteVpn(final Site2SiteVpnConnection conn) throws ResourceUnavailableException {
final Site2SiteVpnGateway vpnGw = _vpnGatewayDao.findById(conn.getVpnGatewayId());
final IpAddress ip = _ipAddressDao.findById(vpnGw.getAddrId());
final Map<Capability, String> vpnCapabilities = capabilities.get(Service.Vpn);
if (!vpnCapabilities.get(Capability.VpnTypes).contains("s2svpn")) {


log.info("try to start site site vpn on unsupported network element");
}
}

};