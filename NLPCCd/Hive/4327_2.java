//,temp,sample_4622.java,2,18,temp,sample_4620.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (request.isSetNeedMerge() && request.isNeedMerge()) {
ColumnStatistics csOld = getMS().getTableColumnStatistics(dbName, tableName, colNames);
Table t = getTable(dbName, tableName);
MetaStoreUtils.getMergableCols(firstColStats, t.getParameters());
if (csOld != null && csOld.getStatsObjSize() != 0 && !firstColStats.getStatsObj().isEmpty()) {
MetaStoreUtils.mergeColStats(firstColStats, csOld);
}
if (!firstColStats.getStatsObj().isEmpty()) {
return update_table_column_statistics(firstColStats);
} else {


log.info("all the column stats are not accurate to merge");
}
}
}

};