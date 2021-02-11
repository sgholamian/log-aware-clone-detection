//,temp,sample_2656.java,2,18,temp,sample_2657.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (!this.serverManager.isServerOnline(server)) {
return;
}
for (byte[] encodedRegionName : encodedRegionNames) {
RegionState regionState = assignmentManager.getRegionStates().getRegionState(Bytes.toString(encodedRegionName));
if (regionState == null) {
continue;
}
RegionInfo hri = regionState.getRegion();
if (server.equals(regionState.getServerName())) {


log.info("skipping move of region because region already assigned to the same server");
}
}
}

};