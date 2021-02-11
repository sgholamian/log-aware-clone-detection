//,temp,sample_5255.java,2,9,temp,sample_476.java,2,9
//,3
public class xxx {
private static long getMergedRegionIdTimestamp(final RegionInfo regionToMergeA, final RegionInfo regionToMergeB) {
long rid = EnvironmentEdgeManager.currentTime();
if (rid < regionToMergeA.getRegionId() || rid < regionToMergeB.getRegionId()) {


log.info("clock skew merging regions id are and but current time here is");
}
}

};