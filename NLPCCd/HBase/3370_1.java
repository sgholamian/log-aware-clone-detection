//,temp,sample_2723.java,2,18,temp,sample_2722.java,2,17
//,3
public class xxx {
public void dummy_method(){
int currentMaxReplica = 0;
for (RegionInfo regionInfo : regionsOfTable) {
if (regionInfo.getReplicaId() > currentMaxReplica) {
currentMaxReplica = regionInfo.getReplicaId();
}
}
int replicasFound = getNumberOfReplicasFromMeta(connection, regionReplicaCount, regionsOfTable);
assert regionReplicaCount - 1 == replicasFound;
if (currentMaxReplica == (regionReplicaCount - 1)) {
if (LOG.isDebugEnabled()) {


log.info("there is no change to the number of region replicas assigning the available regions current and previous replica count is");
}
}
}

};