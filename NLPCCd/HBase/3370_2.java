//,temp,sample_2723.java,2,18,temp,sample_2722.java,2,17
//,3
public class xxx {
public void dummy_method(){
List<RegionInfo> regionsOfTable = env.getAssignmentManager().getRegionStates().getRegionsOfTable(tableName, true);
if (regionReplicaCount > 1) {
int currentMaxReplica = 0;
for (RegionInfo regionInfo : regionsOfTable) {
if (regionInfo.getReplicaId() > currentMaxReplica) {
currentMaxReplica = regionInfo.getReplicaId();
}
}
int replicasFound = getNumberOfReplicasFromMeta(connection, regionReplicaCount, regionsOfTable);
assert regionReplicaCount - 1 == replicasFound;


log.info("meta entries added for the given regionreplicacount for the table");
}
}

};