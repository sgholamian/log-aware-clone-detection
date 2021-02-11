//,temp,sample_5933.java,2,11,temp,sample_5937.java,2,17
//,3
public class xxx {
public void preScannerOpen(final ObserverContext<RegionCoprocessorEnvironment> e, final Scan scan) throws IOException {
int replicaId = e.getEnvironment().getRegion().getRegionInfo().getReplicaId();
if (e.getEnvironment().getRegion().getRegionInfo().getReplicaId() <= 1) {
throw new RegionServerStoppedException("Server " + e.getEnvironment().getServerName() + " not running");
} else {


log.info("we re replica region");
}
}

};