//,temp,DirectPodBasedNetworkGuru.java,85,94,temp,ControlNetworkGuru.java,83,90
//,3
public class xxx {
    @Override
    protected boolean canHandle(NetworkOffering offering, DataCenter dc, PhysicalNetwork physnet) {
        // this guru handles system Direct pod based network in Basic zones only (no isolation type specified)
        if (dc.getNetworkType() == NetworkType.Basic && isMyTrafficType(offering.getTrafficType())) {
            return true;
        } else {
            s_logger.trace("We only take care of Guest Direct Pod based networks");
            return false;
        }
    }

};