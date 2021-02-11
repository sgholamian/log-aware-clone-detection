//,temp,sample_7630.java,2,10,temp,sample_6211.java,2,10
//,2
public class xxx {
public Network design(NetworkOffering offering, DeploymentPlan plan, Network userSpecified, Account owner) {
PhysicalNetworkVO physnet = physicalNetworkDao.findById(plan.getPhysicalNetworkId());
DataCenter dc = _dcDao.findById(plan.getDataCenterId());
if (!canHandle(offering, dc.getNetworkType(), physnet)) {


log.info("refusing to design this network");
}
}

};