//,temp,sample_836.java,2,8,temp,sample_835.java,2,8
//,3
public class xxx {
private RemoveSessionResult handleReturnedInUseSessionOnMasterThread( EventState e, WmTezSession session, HashSet<String> poolsToRedistribute, boolean isReturn) {
if (e.updateErrors.remove(session) != null) {


log.info("ignoring an update error for a session being destroyed or returned");
}
}

};