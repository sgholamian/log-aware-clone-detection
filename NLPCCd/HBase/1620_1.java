//,temp,sample_4310.java,2,11,temp,sample_5935.java,2,13
//,3
public class xxx {
public void prePut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
Region region = e.getEnvironment().getRegion();
if (!region.getRegionInfo().isMetaRegion() && !region.getRegionInfo().getTable().isSystemTable()) {
if (put.getAttribute(TEST_ATR_KEY) != null) {


log.info("allow any put to happen");
}
}
}

};