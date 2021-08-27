//,temp,sample_836.java,2,8,temp,sample_835.java,2,8
//,3
public class xxx {
private void handeReopenRequestOnMasterThread(EventState e, WmTezSession session, SettableFuture<WmTezSession> future, HashSet<String> poolsToRedistribute, WmThreadSyncWork syncWork) throws Exception {
if (e.updateErrors.remove(session) != null) {


log.info("ignoring an update error for a session being reopened");
}
}

};