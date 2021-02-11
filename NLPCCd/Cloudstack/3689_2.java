//,temp,sample_8597.java,2,17,temp,sample_8596.java,2,17
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
if (!stats.isSynchronized()) {


log.info("db and vnc objects out of sync is detected");
}
}
}

};