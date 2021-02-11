//,temp,sample_3798.java,2,17,temp,sample_702.java,2,12
//,3
public class xxx {
public void dummy_method(){
Admin admin = unmanagedConnection.getAdmin();
try {
int numberOfServers = admin.getClusterMetrics(EnumSet.of(Option.LIVE_SERVERS)).getLiveServerMetrics() .size();
if (numberOfServers == 0) {
throw new IllegalStateException("No live regionservers");
}
totalNumberOfRegions = numberOfServers * numRegionsPerServer;
byte[][] splits = splitter.split( totalNumberOfRegions);
admin.createTable(td, splits);
} catch (MasterNotRunningException e) {


log.info("master not running");
}
}

};