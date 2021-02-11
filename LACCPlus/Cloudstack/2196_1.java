//,temp,VxlanGuestNetworkGuru.java,151,160,temp,BigSwitchBcfGuestNetworkGuru.java,309,329
//,3
public class xxx {
    @Override
    public void shutdown(NetworkProfile profile, NetworkOffering offering) {
        NetworkVO networkObject = _networkDao.findById(profile.getId());
        if (networkObject.getBroadcastDomainType() != BroadcastDomainType.Vxlan || networkObject.getBroadcastUri() == null) {
            s_logger.warn("BroadcastUri is empty or incorrect for guestnetwork " + networkObject.getDisplayText());
            return;
        }

        super.shutdown(profile, offering);
    }

};