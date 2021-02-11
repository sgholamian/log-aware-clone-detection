//,temp,sample_5345.java,2,12,temp,sample_5344.java,2,10
//,3
public class xxx {
private void unloadRegions(Admin admin, String server, ArrayList<String> regionServers, boolean ack, List<RegionInfo> movedRegions) throws Exception {
List<RegionInfo> regionsToMove = new ArrayList<>();
regionsToMove = getRegions(this.conf, server);
if (regionsToMove.isEmpty()) {


log.info("no regions to move quitting now");
}
}

};