//,temp,sample_5933.java,2,11,temp,sample_5937.java,2,17
//,3
public class xxx {
public void preScannerOpen(final ObserverContext<RegionCoprocessorEnvironment> e, final Scan scan) throws IOException {
int replicaId = e.getEnvironment().getRegion().getRegionInfo().getReplicaId();
if (e.getEnvironment().getRegion().getRegionInfo().isMetaRegion() && (replicaId == 0)) {
if (slowDownPrimaryMetaScan) {
try {
Thread.sleep(META_SCAN_TIMEOUT_IN_MILLISEC - 50);
} catch (InterruptedException ie) {
}
}
if (throwException) {


log.info("scan throw region server stopped exceptoin for replica");
}
}
}

};