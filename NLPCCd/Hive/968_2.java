//,temp,sample_3038.java,2,19,temp,sample_3035.java,2,20
//,3
public class xxx {
public void dummy_method(){
List<String> neededColsToRetrieve;
List<String> partitionColsToRetrieve;
List<ColStatistics> columnStats = new ArrayList<>();
if (colStatsCache != null) {
neededColsToRetrieve = new ArrayList<String>(neededColumns.size());
for (String colName : neededColumns) {
ColStatistics colStats = colStatsCache.getColStats().get(colName);
if (colStats == null) {
neededColsToRetrieve.add(colName);
if (LOG.isDebugEnabled()) {


log.info("stats for column in table could not be retrieved from cache");
}
}
}
}
}

};