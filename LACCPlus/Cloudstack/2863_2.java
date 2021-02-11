//,temp,StorageNetworkGuru.java,75,82,temp,ControlNetworkGuru.java,83,90
//,2
public class xxx {
    protected boolean canHandle(NetworkOffering offering) {
        if (offering.isSystemOnly() && isMyTrafficType(offering.getTrafficType())) {
            return true;
        } else {
            s_logger.trace("We only care about System only Control network");
            return false;
        }
    }

};