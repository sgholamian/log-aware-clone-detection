//,temp,OpendaylightGuestNetworkGuru.java,101,126,temp,BigSwitchBcfGuestNetworkGuru.java,146,174
//,3
public class xxx {
    @Override
    public Network design(NetworkOffering offering, DeploymentPlan plan, Network userSpecified, Account owner) {
        PhysicalNetworkVO physnet = physicalNetworkDao.findById(plan.getPhysicalNetworkId());
        DataCenter dc = _dcDao.findById(plan.getDataCenterId());
        if (!canHandle(offering, dc.getNetworkType(), physnet)) {
            s_logger.debug("Refusing to design this network");
            return null;
        }

        List<OpenDaylightControllerVO> devices = openDaylightControllerMappingDao.listByPhysicalNetwork(physnet.getId());
        if (devices.isEmpty()) {
            s_logger.error("No Controller on physical network " + physnet.getName());
            return null;
        }
        s_logger.debug("Controller " + devices.get(0).getUuid() + " found on physical network " + physnet.getId());
        s_logger.debug("Physical isolation type is ODL, asking GuestNetworkGuru to design this network");

        NetworkVO networkObject = (NetworkVO)super.design(offering, plan, userSpecified, owner);
        if (networkObject == null) {
            return null;
        }
        // Override the broadcast domain type
        networkObject.setBroadcastDomainType(BroadcastDomainType.OpenDaylight);

        return networkObject;
    }

};