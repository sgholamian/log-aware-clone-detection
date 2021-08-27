//,temp,sample_4336.java,2,17,temp,sample_3036.java,2,19
//,3
public class xxx {
public void dummy_method(){
neededColsToRetrieve = new ArrayList<String>(neededColumns.size());
for (String colName : neededColumns) {
ColStatistics colStats = colStatsCache.getColStats().get(colName);
if (colStats == null) {
neededColsToRetrieve.add(colName);
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