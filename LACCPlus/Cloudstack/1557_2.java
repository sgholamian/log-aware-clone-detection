//,temp,SspElement.java,191,202,temp,SspElement.java,176,185
//,3
public class xxx {
    @Override
    public boolean isReady(PhysicalNetworkServiceProvider provider) {
        PhysicalNetwork physicalNetwork = _physicalNetworkDao.findById(provider.getPhysicalNetworkId());
        assert (physicalNetwork != null);
        if (fetchSspClients(physicalNetwork.getId(), physicalNetwork.getDataCenterId(), false).size() > 0) {
            return true;
        }
        s_logger.warn("Ssp api endpoint not found. " + physicalNetwork.toString());
        return false;
    }

};