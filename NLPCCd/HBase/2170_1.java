//,temp,sample_5936.java,2,11,temp,sample_5933.java,2,11
//,3
public class xxx {
public void preScannerOpen(final ObserverContext<RegionCoprocessorEnvironment> e, final Scan scan) throws IOException {
int replicaId = e.getEnvironment().getRegion().getRegionInfo().getReplicaId();
if (e.getEnvironment().getRegion().getRegionInfo().isMetaRegion() && (replicaId == 0)) {
if (slowDownPrimaryMetaScan) {


log.info("scan with primary meta region slow down a bit");
}
}
}

};