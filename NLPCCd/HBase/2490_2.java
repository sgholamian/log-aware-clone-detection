//,temp,sample_4310.java,2,11,temp,sample_5930.java,2,9
//,3
public class xxx {
public void preGetOp(final ObserverContext<RegionCoprocessorEnvironment> e, final Get get, final List<Cell> results) throws IOException {
int replicaId = e.getEnvironment().getRegion().getRegionInfo().getReplicaId();
if (e.getEnvironment().getRegion().getRegionInfo().getReplicaId() <= 1) {


log.info("throw region server stopped exceptoin for replica id");
}
}

};