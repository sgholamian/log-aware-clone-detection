//,temp,sample_7631.java,2,14,temp,sample_7632.java,2,15
//,3
public class xxx {
public Network design(NetworkOffering offering, DeploymentPlan plan, Network userSpecified, Account owner) {
PhysicalNetworkVO physnet = physicalNetworkDao.findById(plan.getPhysicalNetworkId());
DataCenter dc = _dcDao.findById(plan.getDataCenterId());
if (!canHandle(offering, dc.getNetworkType(), physnet)) {
return null;
}
List<OpenDaylightControllerVO> devices = openDaylightControllerMappingDao.listByPhysicalNetwork(physnet.getId());
if (devices.isEmpty()) {
return null;
}


log.info("controller found on physical network");
}

};