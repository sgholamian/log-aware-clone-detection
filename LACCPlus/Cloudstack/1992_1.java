//,temp,NiciraNvpGuestNetworkGuru.java,140,168,temp,BigSwitchBcfGuestNetworkGuru.java,146,174
//,3
public class xxx {
    @Override
    public Network design(final NetworkOffering offering, final DeploymentPlan plan, final Network userSpecified, final Account owner) {
        // Check of the isolation type of the related physical network is supported
        final PhysicalNetworkVO physnet = physicalNetworkDao.findById(plan.getPhysicalNetworkId());
        final DataCenter dc = _dcDao.findById(plan.getDataCenterId());
        if (!canHandle(offering, dc.getNetworkType(), physnet)) {
            s_logger.debug("Refusing to design this network");
            return null;
        }

        final List<NiciraNvpDeviceVO> devices = niciraNvpDao.listByPhysicalNetwork(physnet.getId());
        if (devices.isEmpty()) {
            s_logger.error("No NiciraNvp Controller on physical network " + physnet.getName());
            return null;
        }
        s_logger.debug("Nicira Nvp " + devices.get(0).getUuid() + " found on physical network " + physnet.getId());

        s_logger.debug("Physical isolation type is supported, asking GuestNetworkGuru to design this network");
        final NetworkVO networkObject = (NetworkVO) super.design(offering, plan, userSpecified, owner);
        if (networkObject == null) {
            return null;
        }
        networkObject.setBroadcastDomainType(BroadcastDomainType.Lswitch);
        if (offering.getGuestType().equals(GuestType.Shared)){
            networkObject.setState(State.Allocated);
        }

        return networkObject;
    }

};