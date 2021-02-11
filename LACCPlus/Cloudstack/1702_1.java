//,temp,BrocadeVcsGuestNetworkGuru.java,99,118,temp,OvsGuestNetworkGuru.java,92,112
//,3
public class xxx {
    @Override
    public Network design(NetworkOffering offering, DeploymentPlan plan, Network userSpecified, Account owner) {
        // Check of the isolation type of the related physical network is VLAN
        PhysicalNetworkVO physnet = _physicalNetworkDao.findById(plan.getPhysicalNetworkId());
        DataCenter dc = _dcDao.findById(plan.getDataCenterId());
        if (!canHandle(offering, dc.getNetworkType(), physnet)) {
            s_logger.debug("Refusing to design this network");
            return null;
        }
        s_logger.debug("Physical isolation type is VCS, asking GuestNetworkGuru to design this network");
        NetworkVO networkObject = (NetworkVO)super.design(offering, plan, userSpecified, owner);
        if (networkObject == null) {
            return null;
        }
        // Override the broadcast domain type
        networkObject.setBroadcastDomainType(BroadcastDomainType.Vcs);
        networkObject.setState(State.Allocated);

        return networkObject;
    }

};