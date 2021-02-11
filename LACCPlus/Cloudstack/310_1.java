//,temp,PrivateNetworkGuru.java,88,97,temp,OpendaylightGuestNetworkGuru.java,89,99
//,3
public class xxx {
    protected boolean canHandle(NetworkOffering offering, DataCenter dc) {
        // This guru handles only system Guest network
        if (dc.getNetworkType() == NetworkType.Advanced && isMyTrafficType(offering.getTrafficType()) && offering.getGuestType() == Network.GuestType.Isolated &&
            offering.isSystemOnly()) {
            return true;
        } else {
            s_logger.trace("We only take care of system Guest networks of type   " + GuestType.Isolated + " in zone of type " + NetworkType.Advanced);
            return false;
        }
    }

};