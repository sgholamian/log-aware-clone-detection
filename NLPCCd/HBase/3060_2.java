//,temp,sample_451.java,2,13,temp,sample_1206.java,2,9
//,3
public class xxx {
public boolean reportRegionSizesForQuotas(final Map<RegionInfo, Long> onlineRegionSizes) {
RegionServerStatusService.BlockingInterface rss = rssStub;
if (rss == null) {


log.info("skipping region size report to hmaster as stub is null");
}
}

};