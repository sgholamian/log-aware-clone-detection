//,temp,sample_2743.java,2,12,temp,sample_2742.java,2,11
//,2
public class xxx {
public boolean canHandle(PhysicalNetwork physicalNetwork) {
if (physicalNetwork != null) {
if (fetchSspClients(physicalNetwork.getId(), physicalNetwork.getDataCenterId(), true).size() > 0) {
return true;
}


log.info("enabled ssp api endpoint not found");
}
}

};