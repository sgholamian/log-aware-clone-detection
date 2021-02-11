//,temp,sample_3336.java,2,9,temp,sample_2002.java,2,9
//,2
public class xxx {
public Network design(NetworkOffering offering, DeploymentPlan plan, Network userSpecified, Account owner) {
PhysicalNetworkVO physnet = _physicalNetworkDao.findById(plan.getPhysicalNetworkId());
if (physnet == null || physnet.getIsolationMethods() == null || !physnet.getIsolationMethods().contains("MIDO")) {


log.info("refusing to design this network the physical isolation type is not mido");
}
}

};