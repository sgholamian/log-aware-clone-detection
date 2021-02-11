//,temp,OpendaylightGuestNetworkGuru.java,101,126,temp,BigSwitchBcfGuestNetworkGuru.java,146,174
//,3
public class xxx {
    @Override
    public Network design(NetworkOffering offering, DeploymentPlan plan, Network userSpecified, Account owner) {
        // Check if the isolation type of the physical network is BCF_SEGMENT, then delegate GuestNetworkGuru to design
        PhysicalNetworkVO physnet = _physicalNetworkDao.findById(plan.getPhysicalNetworkId());
        if (physnet == null || physnet.getIsolationMethods() == null || !physnet.getIsolationMethods().contains("BCF_SEGMENT")) {
            s_logger.debug("Refusing to design this network, the physical isolation type is not BCF_SEGMENT");
            return null;
        }

        List<BigSwitchBcfDeviceVO> devices = _bigswitchBcfDao.listByPhysicalNetwork(physnet.getId());
        if (devices.isEmpty()) {
            s_logger.error("No BigSwitch Controller on physical network " + physnet.getName());
            return null;
        }
        for (BigSwitchBcfDeviceVO d: devices){
            s_logger.debug("BigSwitch Controller " + d.getUuid()
                    + " found on physical network " + physnet.getId());
        }

        s_logger.debug("Physical isolation type is BCF_SEGMENT, asking GuestNetworkGuru to design this network");
        NetworkVO networkObject = (NetworkVO)super.design(offering, plan, userSpecified, owner);
        if (networkObject == null) {
            return null;
        }
        // Override the broadcast domain type
        networkObject.setBroadcastDomainType(BroadcastDomainType.Vlan);

        return networkObject;
    }

};