//,temp,sample_2656.java,2,18,temp,sample_2657.java,2,18
//,3
public class xxx {
public void dummy_method(){
this.serverManager.removeServerFromDrainList(server);
if (encodedRegionNames == null || encodedRegionNames.isEmpty()) {
return;
}
if (!this.serverManager.isServerOnline(server)) {
return;
}
for (byte[] encodedRegionName : encodedRegionNames) {
RegionState regionState = assignmentManager.getRegionStates().getRegionState(Bytes.toString(encodedRegionName));
if (regionState == null) {


log.info("unknown region");
}
}
}

};