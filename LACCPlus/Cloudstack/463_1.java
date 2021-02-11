//,temp,GuestNetworkGuru.java,427,442,temp,OvsGuestNetworkGuru.java,179,195
//,3
public class xxx {
    @Override
    public void shutdown(final NetworkProfile profile, final NetworkOffering offering) {
        if (profile.getBroadcastUri() == null) {
            return; // Nothing to do here if the uri is null already
        }

        if ((profile.getBroadcastDomainType() == BroadcastDomainType.Vlan || profile.getBroadcastDomainType() == BroadcastDomainType.Vxlan) && !offering.isSpecifyVlan()) {
            s_logger.debug("Releasing vnet for the network id=" + profile.getId());
            _dcDao.releaseVnet(BroadcastDomainType.getValue(profile.getBroadcastUri()), profile.getDataCenterId(), profile.getPhysicalNetworkId(), profile.getAccountId(),
                    profile.getReservationId());
            ActionEventUtils.onCompletedActionEvent(CallContext.current().getCallingUserId(), profile.getAccountId(), EventVO.LEVEL_INFO, EventTypes.EVENT_ZONE_VLAN_RELEASE,
                    "Released Zone Vnet: " + BroadcastDomainType.getValue(profile.getBroadcastUri()) + " for Network: " + profile.getId(), 0);
        }

        profile.setBroadcastUri(null);
    }

};