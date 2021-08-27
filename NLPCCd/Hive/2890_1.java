//,temp,sample_3375.java,2,16,temp,sample_3376.java,2,17
//,3
public class xxx {
public void dummy_method(){
List<ColStatistics> columnStats = new ArrayList<>();
for (String key : keys) {
ColStatistics cs = inputStats.getColumnStatisticsFromColName(key);
if (cs == null) {
return true;
}
columnStats.add(cs);
}
long numRows = inputStats.getNumRows();
long estimation = estimateNDV(numRows, columnStats);


log.info("estimated ndv for input max ndv for mapjoin conversion");
}

};