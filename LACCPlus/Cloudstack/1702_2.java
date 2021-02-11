//,temp,BrocadeVcsGuestNetworkGuru.java,99,118,temp,OvsGuestNetworkGuru.java,92,112
//,3
public class xxx {
    @Override
    public Network design(NetworkOffering offering, DeploymentPlan plan,
        Network userSpecified, Account owner) {

        PhysicalNetworkVO physnet = _physicalNetworkDao.findById(plan
            .getPhysicalNetworkId());
        DataCenter dc = _dcDao.findById(plan.getDataCenterId());
        if (!canHandle(offering, dc.getNetworkType(), physnet)) {
            s_logger.debug("Refusing to design this network");
            return null;
        }
        NetworkVO config = (NetworkVO)super.design(offering, plan,
            userSpecified, owner);
        if (config == null) {
            return null;
        }

        config.setBroadcastDomainType(BroadcastDomainType.Vswitch);

        return config;
    }

};