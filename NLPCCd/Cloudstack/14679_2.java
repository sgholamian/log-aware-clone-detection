//,temp,sample_7630.java,2,10,temp,sample_1981.java,2,10
//,2
public class xxx {
public Network design(final NetworkOffering offering, final DeploymentPlan plan, final Network userSpecified, final Account owner) {
final PhysicalNetworkVO physnet = physicalNetworkDao.findById(plan.getPhysicalNetworkId());
final DataCenter dc = _dcDao.findById(plan.getDataCenterId());
if (!canHandle(offering, dc.getNetworkType(), physnet)) {


log.info("refusing to design this network");
}
}

};