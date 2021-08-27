//,temp,sample_4454.java,2,18,temp,sample_4452.java,2,17
//,3
public class xxx {
public void dummy_method(){
Deadline.stopTimer();
if (colStatsForDB != null) {
sharedCache.addPartitionColStatsToCache(colStatsForDB);
}
Database db = rawStore.getDatabase(dbName);
sharedCache.addDatabaseToCache(dbName, db);
List<String> tblNames = rawStore.getAllTables(dbName);
for (int j = 0; j < tblNames.size(); j++) {
String tblName = StringUtils.normalizeIdentifier(tblNames.get(j));
if (!shouldCacheTable(dbName, tblName)) {


log.info("not caching database s table");
}
}
}

};