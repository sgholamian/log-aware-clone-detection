//,temp,sample_4310.java,2,11,temp,sample_5935.java,2,13
//,3
public class xxx {
public void preGetOp(final ObserverContext<RegionCoprocessorEnvironment> e, final Get get, final List<Cell> results) throws IOException {
int replicaId = e.getEnvironment().getRegion().getRegionInfo().getReplicaId();
if (throwException) {
if (!e.getEnvironment().getRegion().getRegionInfo().isMetaRegion() && (replicaId == 0)) {
throw new RegionServerStoppedException("Server " + e.getEnvironment().getServerName() + " not running");
}
} else {


log.info("get we re replica region");
}
}

};