//,temp,VxlanGuestNetworkGuru.java,151,160,temp,BigSwitchBcfGuestNetworkGuru.java,309,329
//,3
public class xxx {
    @Override
    public void shutdown(NetworkProfile profile, NetworkOffering offering) {
        NetworkVO networkObject = _networkDao.findById(profile.getId());
        if (networkObject.getBroadcastDomainType() != BroadcastDomainType.Vlan || networkObject.getBroadcastUri() == null) {
            s_logger.warn("BroadcastUri is empty or incorrect for guestnetwork " + networkObject.getDisplayText());
            return;
        }

        bcfUtilsInit();

        // tenantId stored in network domain field at creation
        String tenantId = networkObject.getNetworkDomain();

        String networkId = networkObject.getUuid();

        DeleteBcfSegmentCommand cmd = new DeleteBcfSegmentCommand(tenantId, networkId);

        _bcfUtils.sendBcfCommandWithNetworkSyncCheck(cmd, networkObject);

        super.shutdown(profile, offering);
    }

};