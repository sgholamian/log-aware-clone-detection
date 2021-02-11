//,temp,NiciraNvpGuestNetworkGuru.java,277,307,temp,OpendaylightGuestNetworkGuru.java,242,265
//,3
public class xxx {
    @Override
    public void shutdown(final NetworkProfile profile, final NetworkOffering offering) {
        final NetworkVO networkObject = networkDao.findById(profile.getId());
        if (networkObject.getBroadcastDomainType() != BroadcastDomainType.Lswitch || networkObject.getBroadcastUri() == null) {
            s_logger.warn("BroadcastUri is empty or incorrect for guestnetwork " + networkObject.getDisplayText());
            return;
        }

        final List<NiciraNvpDeviceVO> devices = niciraNvpDao.listByPhysicalNetwork(networkObject.getPhysicalNetworkId());
        if (devices.isEmpty()) {
            s_logger.error("No NiciraNvp Controller on physical network " + networkObject.getPhysicalNetworkId());
            return;
        }
        final NiciraNvpDeviceVO niciraNvpDevice = devices.get(0);
        final HostVO niciraNvpHost = hostDao.findById(niciraNvpDevice.getHostId());

        String logicalSwitchUuid = BroadcastDomainType.getValue(networkObject.getBroadcastUri());

        if (offering.getGuestType().equals(GuestType.Shared)){
            sharedNetworksCleanup(networkObject, logicalSwitchUuid, niciraNvpHost);
        }

        final DeleteLogicalSwitchCommand cmd = new DeleteLogicalSwitchCommand(logicalSwitchUuid);
        final DeleteLogicalSwitchAnswer answer = (DeleteLogicalSwitchAnswer) agentMgr.easySend(niciraNvpHost.getId(), cmd);

        if (answer == null || !answer.getResult()) {
            s_logger.error("DeleteLogicalSwitchCommand failed");
        }

        super.shutdown(profile, offering);
    }

};