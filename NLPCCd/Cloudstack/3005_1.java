//,temp,sample_1982.java,2,14,temp,sample_1983.java,2,15
//,3
public class xxx {
public Network design(final NetworkOffering offering, final DeploymentPlan plan, final Network userSpecified, final Account owner) {
final PhysicalNetworkVO physnet = physicalNetworkDao.findById(plan.getPhysicalNetworkId());
final DataCenter dc = _dcDao.findById(plan.getDataCenterId());
if (!canHandle(offering, dc.getNetworkType(), physnet)) {
return null;
}
final List<NiciraNvpDeviceVO> devices = niciraNvpDao.listByPhysicalNetwork(physnet.getId());
if (devices.isEmpty()) {


log.info("no niciranvp controller on physical network");
}
}

};