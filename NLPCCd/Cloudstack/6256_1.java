//,temp,sample_3985.java,2,12,temp,sample_3988.java,2,12
//,3
public class xxx {
private Answer executeRequest(ImplementNetworkVspCommand cmd) {
try {
isNuageVspGuruLoaded();
_nuageVspGuruClient.implement(cmd.getNetwork(), cmd.getDnsServers());
return new Answer(cmd, true, "Created network mapping to " + cmd.getNetwork().getName() + " on Nuage VSD " + _hostName);
} catch (ExecutionException | ConfigurationException e) {


log.info("failure during on nuage vsd");
}
}

};