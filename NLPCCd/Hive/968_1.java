//,temp,sample_3038.java,2,19,temp,sample_3035.java,2,20
//,3
public class xxx {
public void dummy_method(){
partitionColsToRetrieve = new ArrayList<>(partitionCols.size());
for (String colName : partitionCols) {
ColStatistics colStats = colStatsCache.getColStats().get(colName);
if (colStats == null) {
partitionColsToRetrieve.add(colName);
if (LOG.isDebugEnabled()) {
}
} else {
columnStats.add(colStats);
if (LOG.isDebugEnabled()) {


log.info("stats for column in table retrieved from cache");
}
}
}
}

};