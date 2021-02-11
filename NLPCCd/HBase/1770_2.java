//,temp,sample_2943.java,2,20,temp,sample_2939.java,2,15
//,3
public class xxx {
protected void addRegion(final HRegion region, RegionVisitor visitor) throws IOException {
Object regionData = visitor.regionOpen(region.getRegionInfo());
monitor.rethrowException();
for (HStore store : region.getStores()) {
Object familyData = visitor.familyOpen(regionData, store.getColumnFamilyDescriptor().getName());
monitor.rethrowException();
List<HStoreFile> storeFiles = new ArrayList<>(store.getStorefiles());
if (LOG.isDebugEnabled()) {


log.info("adding snapshot references for hfiles");
}
}
}

};