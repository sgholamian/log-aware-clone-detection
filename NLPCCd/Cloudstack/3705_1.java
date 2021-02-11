//,temp,sample_7542.java,2,18,temp,sample_7541.java,2,18
//,2
public class xxx {
public void dummy_method(){
Long physicalNetworkId = network.getPhysicalNetworkId();
List<BrocadeVcsDeviceVO> devices = _brocadeVcsDao.listByPhysicalNetwork(physicalNetworkId);
if (devices.isEmpty()) {
return null;
}
for (BrocadeVcsDeviceVO brocadeVcsDevice : devices) {
HostVO brocadeVcsHost = _hostDao.findById(brocadeVcsDevice.getHostId());
CreateNetworkCommand cmd = new CreateNetworkCommand(vlanTag, network.getId(), context.getDomain().getName() + "-" + context.getAccount().getAccountName());
CreateNetworkAnswer answer = (CreateNetworkAnswer)_agentMgr.easySend(brocadeVcsHost.getId(), cmd);
if (answer == null || !answer.getResult()) {


log.info("unable to create network");
}
}
}

};