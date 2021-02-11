//,temp,sample_7547.java,2,18,temp,sample_7546.java,2,18
//,2
public class xxx {
public void dummy_method(){
String interfaceMac = nic.getMacAddress();
List<BrocadeVcsDeviceVO> devices = _brocadeVcsDao.listByPhysicalNetwork(network.getPhysicalNetworkId());
if (devices.isEmpty()) {
return;
}
for (BrocadeVcsDeviceVO brocadeVcsDevice : devices) {
HostVO brocadeVcsHost = _hostDao.findById(brocadeVcsDevice.getHostId());
DisassociateMacFromNetworkCommand cmd = new DisassociateMacFromNetworkCommand(network.getId(), interfaceMac);
DisassociateMacFromNetworkAnswer answer = (DisassociateMacFromNetworkAnswer)_agentMgr.easySend(brocadeVcsHost.getId(), cmd);
if (answer == null || !answer.getResult()) {


log.info("unable to disassociate mac from network");
}
}
}

};