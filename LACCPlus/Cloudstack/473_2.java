//,temp,NiciraNvpGuestNetworkGuru.java,277,307,temp,OpendaylightGuestNetworkGuru.java,242,265
//,3
public class xxx {
    @Override
    public void shutdown(NetworkProfile profile, NetworkOffering offering) {
        NetworkVO networkObject = networkDao.findById(profile.getId());
        if (networkObject.getBroadcastDomainType() != BroadcastDomainType.OpenDaylight || networkObject.getBroadcastUri() == null) {
            s_logger.warn("BroadcastUri is empty or incorrect for guestnetwork " + networkObject.getDisplayText());
            return;
        }

        List<OpenDaylightControllerVO> devices = openDaylightControllerMappingDao.listByPhysicalNetwork(networkObject.getPhysicalNetworkId());
        if (devices.isEmpty()) {
            s_logger.error("No Controller on physical network " + networkObject.getPhysicalNetworkId());
            return;
        }
        OpenDaylightControllerVO controller = devices.get(0);

        DestroyNetworkCommand cmd = new DestroyNetworkCommand(BroadcastDomainType.getValue(networkObject.getBroadcastUri()));
        DestroyNetworkAnswer answer = (DestroyNetworkAnswer)agentManager.easySend(controller.getHostId(), cmd);

        if (answer == null || !answer.getResult()) {
            s_logger.error("DestroyNetworkCommand failed");
        }

        super.shutdown(profile, offering);
    }

};