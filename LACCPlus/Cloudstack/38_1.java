//,temp,StorageNetworkGuru.java,75,82,temp,DirectPodBasedNetworkGuru.java,85,94
//,3
public class xxx {
    protected boolean canHandle(NetworkOffering offering) {
        if (isMyTrafficType(offering.getTrafficType()) && offering.isSystemOnly()) {
            return true;
        } else {
            s_logger.trace("It's not storage network offering, skip it.");
            return false;
        }
    }

};