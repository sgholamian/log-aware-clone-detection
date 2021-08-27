//,temp,sample_1279.java,2,12,temp,sample_1278.java,2,12
//,3
public class xxx {
public synchronized void updatePartitionColStatsInCache(String dbName, String tableName, List<String> partVals, List<ColumnStatisticsObj> colStatsObjs) {
for (ColumnStatisticsObj colStatObj : colStatsObjs) {
String key = CacheUtils.buildKey(dbName, tableName, partVals, colStatObj.getColName());
ColumnStatisticsObj oldStatsObj = partitionColStatsCache.get(key);
if (oldStatsObj != null) {


log.info("cachedstore updating partition column stats for column of table and database");
}
}
}

};