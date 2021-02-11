//,temp,sample_3985.java,2,12,temp,sample_3988.java,2,12
//,3
public class xxx {
private Answer executeRequest(TrashNetworkVspCommand cmd) {
try {
isNuageVspGuruLoaded();
_nuageVspGuruClient.trash(cmd.getNetwork());
return new Answer(cmd, true, "Deleted network mapping to " + cmd.getNetwork().getUuid() + " on Nuage VSD " + _hostName);
} catch (ExecutionException | ConfigurationException e) {


log.info("failure during on nuage vsd");
}
}

};