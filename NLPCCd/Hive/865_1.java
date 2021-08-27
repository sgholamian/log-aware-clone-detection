//,temp,sample_816.java,2,18,temp,sample_815.java,2,15
//,3
public class xxx {
public void dummy_method(){
HashSet<String> poolsToRedistribute = new HashSet<>();
for (SessionInitContext sw : e.initResults) {
handleInitResultOnMasterThread(sw, syncWork, poolsToRedistribute);
}
e.initResults.clear();
for (Map.Entry<WmTezSession, Boolean> entry : e.killQueryResults.entrySet()) {
WmTezSession killQuerySession = entry.getKey();
boolean killResult = entry.getValue();
KillQueryContext killCtx = killQueryInProgress.get(killQuerySession);
if (killCtx == null) {


log.info("internal error cannot find the context for killing");
}
}
}

};