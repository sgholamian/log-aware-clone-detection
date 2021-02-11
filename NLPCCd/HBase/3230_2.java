//,temp,sample_2435.java,2,11,temp,sample_2192.java,2,16
//,3
public class xxx {
private void monitorRegionServers(Map<String, List<RegionInfo>> rsAndRMap, RegionServerStdOutSink regionServerSink) {
List<RegionServerTask> tasks = new ArrayList<>();
Map<String, AtomicLong> successMap = new HashMap<>();
Random rand = new Random();
for (Map.Entry<String, List<RegionInfo>> entry : rsAndRMap.entrySet()) {
String serverName = entry.getKey();
AtomicLong successes = new AtomicLong(0);
successMap.put(serverName, successes);
if (entry.getValue().isEmpty()) {


log.info("regionserver not serving any regions s");
}
}
}

};