//,temp,sample_5255.java,2,9,temp,sample_476.java,2,9
//,3
public class xxx {
private static long getDaughterRegionIdTimestamp(final RegionInfo hri) {
long rid = EnvironmentEdgeManager.currentTime();
if (rid < hri.getRegionId()) {


log.info("clock skew parent regions id is but current time here is");
}
}

};