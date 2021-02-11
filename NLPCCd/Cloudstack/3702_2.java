//,temp,sample_1396.java,2,13,temp,sample_1395.java,2,13
//,2
public class xxx {
public boolean applyStaticNatRules(Network network, List<? extends StaticNat> rules) throws ResourceUnavailableException {
long zoneId = network.getDataCenterId();
DataCenterVO zone = _dcDao.findById(zoneId);
ExternalFirewallDeviceVO fwDeviceVO = getExternalFirewallForNetwork(network);
HostVO externalFirewall = _hostDao.findById(fwDeviceVO.getHostId());
assert (externalFirewall != null);
if (network.getState() == Network.State.Allocated) {


log.info("external firewall was asked to apply firewall rules for network with id this network is not implemented skipping backend commands");
}
}

};