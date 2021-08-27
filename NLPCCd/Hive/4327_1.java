//,temp,sample_4622.java,2,18,temp,sample_4620.java,2,18
//,3
public class xxx {
public void dummy_method(){
ColumnStatistics csNew = entry.getValue();
ColumnStatistics csOld = oldStatsMap.get(entry.getKey());
if (request.isSetNeedMerge() && request.isNeedMerge()) {
MetaStoreUtils.getMergableCols(csNew, mapToPart.get(entry.getKey()).getParameters());
if (csOld != null && csOld.getStatsObjSize() != 0 && !csNew.getStatsObj().isEmpty()) {
MetaStoreUtils.mergeColStats(csNew, csOld);
}
if (!csNew.getStatsObj().isEmpty()) {
ret = ret && updatePartitonColStats(t, csNew);
} else {


log.info("all the column stats are not accurate to merge");
}
}
}

};