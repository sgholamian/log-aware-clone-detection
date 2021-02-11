//,temp,GuestNetworkGuru.java,427,442,temp,OvsGuestNetworkGuru.java,179,195
//,3
public class xxx {
    @Override
    public void shutdown(NetworkProfile profile, NetworkOffering offering) {
        NetworkVO networkObject = _networkDao.findById(profile.getId());
        if (networkObject.getBroadcastDomainType() != BroadcastDomainType.Vswitch
            || networkObject.getBroadcastUri() == null) {
            s_logger.warn("BroadcastUri is empty or incorrect for guestnetwork "
                + networkObject.getDisplayText());
            return;
        }

        if (profile.getBroadcastDomainType() == BroadcastDomainType.Vswitch ) {
            s_logger.debug("Releasing vnet for the network id=" + profile.getId());
            _dcDao.releaseVnet(BroadcastDomainType.getValue(profile.getBroadcastUri()), profile.getDataCenterId(), profile.getPhysicalNetworkId(),
                    profile.getAccountId(), profile.getReservationId());
        }
        profile.setBroadcastUri(null);
    }

};