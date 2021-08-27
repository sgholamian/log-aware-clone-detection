//,temp,sample_1279.java,2,12,temp,sample_1278.java,2,12
//,3
public class xxx {
public synchronized void updateTableColStatsInCache(String dbName, String tableName, List<ColumnStatisticsObj> colStatsForTable) {
for (ColumnStatisticsObj colStatObj : colStatsForTable) {
String key = CacheUtils.buildKey(dbName, tableName, colStatObj.getColName());
ColumnStatisticsObj oldStatsObj = tableColStatsCache.get(key);
if (oldStatsObj != null) {


log.info("cachedstore updating table column stats for column of table and database");
}
}
}

};