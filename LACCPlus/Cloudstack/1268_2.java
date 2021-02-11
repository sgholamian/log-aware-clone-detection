//,temp,ContrailGuru.java,121,139,temp,ManagementNetworkGuru.java,113,128
//,3
public class xxx {
    @Override
    public Network design(NetworkOffering offering, DeploymentPlan plan, Network userSpecified, Account owner) {

        if (!canHandle(offering)) {
            return null;
        }
        NetworkVO network =
            new NetworkVO(offering.getTrafficType(), Mode.Dhcp, BroadcastDomainType.Lswitch, offering.getId(), Network.State.Allocated, plan.getDataCenterId(),
                plan.getPhysicalNetworkId(), offering.isRedundantRouter());
        if (_mgmtCidr != null) {
            network.setCidr(_mgmtCidr);
            network.setGateway(_mgmtGateway);
        }
        s_logger.debug("Allocated network " + userSpecified.getName() + (network.getCidr() == null ? "" : " subnet: " + network.getCidr()));
        return network;
    }

};