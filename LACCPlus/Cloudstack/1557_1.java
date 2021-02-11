//,temp,SspElement.java,191,202,temp,SspElement.java,176,185
//,3
public class xxx {
    @Override
    public boolean canHandle(PhysicalNetwork physicalNetwork) {
        if (physicalNetwork != null) {
            if (fetchSspClients(physicalNetwork.getId(), physicalNetwork.getDataCenterId(), true).size() > 0) {
                return true;
            }
            s_logger.warn("enabled Ssp api endpoint not found. " + physicalNetwork.toString());
        } else {
            s_logger.warn("PhysicalNetwork is NULL.");
        }
        return false;
    }

};