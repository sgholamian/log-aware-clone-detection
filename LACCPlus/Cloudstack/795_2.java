//,temp,DirectPodBasedNetworkGuru.java,85,94,temp,ControlNetworkGuru.java,83,90
//,3
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