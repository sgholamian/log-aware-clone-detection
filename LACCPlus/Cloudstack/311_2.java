//,temp,PrivateNetworkGuru.java,88,97,temp,OvsGuestNetworkGuru.java,72,90
//,3
public class xxx {
    @Override
    protected boolean canHandle(NetworkOffering offering,
        final NetworkType networkType, final PhysicalNetwork physicalNetwork) {
        // This guru handles only Guest Isolated network that supports Source
        // nat service
        if (networkType == NetworkType.Advanced
            && isMyTrafficType(offering.getTrafficType())
            && offering.getGuestType() == Network.GuestType.Isolated
            && isMyIsolationMethod(physicalNetwork)
            && _ntwkOfferingSrvcDao.areServicesSupportedByNetworkOffering(
                offering.getId(), Service.Connectivity)) {
            return true;
        } else {
            s_logger.trace("We only take care of Guest networks of type   "
                + GuestType.Isolated + " in zone of type "
                + NetworkType.Advanced);
            return false;
        }
    }

};