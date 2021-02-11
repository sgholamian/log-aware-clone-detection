//,temp,ExternalGuestNetworkGuru.java,90,102,temp,OpendaylightGuestNetworkGuru.java,89,99
//,3
public class xxx {
    @Override
    protected boolean canHandle(NetworkOffering offering, NetworkType networkType, PhysicalNetwork physicalNetwork) {
        if (networkType == NetworkType.Advanced && isMyTrafficType(offering.getTrafficType()) && offering.getGuestType() == Network.GuestType.Isolated &&
                isMyIsolationMethod(physicalNetwork) && ntwkOfferingSrvcDao.areServicesSupportedByNetworkOffering(offering.getId(), Service.Connectivity)
                && ntwkOfferingSrvcDao.isProviderForNetworkOffering(offering.getId(), Provider.Opendaylight)) {
            return true;
        } else {
            s_logger.trace("We only take care of Guest networks of type   " + GuestType.Isolated + " in zone of type " + NetworkType.Advanced);
            return false;
        }
    }

};