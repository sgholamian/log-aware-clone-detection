//,temp,sample_3840.java,2,11,temp,sample_5548.java,2,11
//,2
public class xxx {
public void setUpCluster() throws Exception {
util = getTestingUtil(getConf());
util.initializeCluster(1);
int replicaCount = getConf().getInt(NUM_REPLICA_COUNT_KEY, NUM_REPLICA_COUNT_DEFAULT);
if (LOG.isDebugEnabled() && replicaCount != NUM_REPLICA_COUNT_DEFAULT) {


log.info("region replicas enabled");
}
}

};