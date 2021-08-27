//,temp,sample_4271.java,2,18,temp,sample_5541.java,2,18
//,2
public class xxx {
public void dummy_method(){
String colType = null;
String colName = null;
boolean doAllPartitionContainStats = partNames.size() == colStatsWithSourceInfo.size();
NumDistinctValueEstimator ndvEstimator = null;
for (ColStatsObjWithSourceInfo csp : colStatsWithSourceInfo) {
ColumnStatisticsObj cso = csp.getColStatsObj();
if (statsObj == null) {
colName = cso.getColName();
colType = cso.getColType();
statsObj = ColumnStatsAggregatorFactory.newColumnStaticsObj(colName, colType, cso.getStatsData().getSetField());


log.info("doallpartitioncontainstats for column is");
}
}
}

};