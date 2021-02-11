//,temp,sample_8595.java,2,15,temp,sample_8594.java,2,14
//,2
public class xxx {
public boolean syncGeneric(Class<?> cls, List<?> dbList, List<?> vncList) throws Exception {
SyncStats stats = new SyncStats();
stats.log("Sync log for <" + getClassName(cls) + ">");
java.util.Collections.sort(dbList, this.dbComparator(cls));
java.util.Collections.sort(vncList, this.vncComparator(cls));
syncCollections(cls, dbList, vncList, _syncMode != SYNC_MODE_CHECK, stats);
if (_syncMode != SYNC_MODE_CHECK) {
s_logger.debug(stats.logMsg);
} else {


log.info("sync state checking stats");
}
}

};