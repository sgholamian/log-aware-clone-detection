//,temp,ContrailGuru.java,121,139,temp,ManagementNetworkGuru.java,113,128
//,3
public class xxx {
    @Override
    public Network design(NetworkOffering offering, DeploymentPlan plan, Network userSpecified, Account owner) {
        // Check of the isolation type of the related physical network is L3VPN
        PhysicalNetworkVO physnet = _physicalNetworkDao.findById(plan.getPhysicalNetworkId());
        DataCenter dc = _dcDao.findById(plan.getDataCenterId());
        if (!canHandle(offering, dc.getNetworkType(),physnet)) {
            s_logger.debug("Refusing to design this network");
            return null;
        }
        NetworkVO network =
                new NetworkVO(offering.getTrafficType(), Mode.Dhcp, BroadcastDomainType.Lswitch, offering.getId(), State.Allocated, plan.getDataCenterId(),
                        plan.getPhysicalNetworkId(), offering.isRedundantRouter());
        if (userSpecified.getCidr() != null) {
            network.setCidr(userSpecified.getCidr());
            network.setGateway(userSpecified.getGateway());
        }
        s_logger.debug("Allocated network " + userSpecified.getName() + (network.getCidr() == null ? "" : " subnet: " + network.getCidr()));
        return network;
    }

};