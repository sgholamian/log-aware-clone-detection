//,temp,sample_4454.java,2,18,temp,sample_4452.java,2,17
//,3
public class xxx {
public void dummy_method(){
List<String> dbNames = rawStore.getAllDatabases();
SharedCache sharedCache = sharedCacheWrapper.getUnsafe();
for (int i = 0; i < dbNames.size(); i++) {
String dbName = StringUtils.normalizeIdentifier(dbNames.get(i));
Deadline.startTimer("getColStatsForDatabase");
List<ColStatsObjWithSourceInfo> colStatsForDB = rawStore.getPartitionColStatsForDatabase(dbName);
Deadline.stopTimer();
if (colStatsForDB != null) {
sharedCache.addPartitionColStatsToCache(colStatsForDB);
}


log.info("caching database cached databases so far");
}
}

};