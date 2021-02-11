//,temp,ReplicationSourceFactory.java,35,53,temp,CleanerChore.java,270,284
//,3
public class xxx {
  static ReplicationSourceInterface create(Configuration conf, String queueId) {
    ReplicationQueueInfo replicationQueueInfo = new ReplicationQueueInfo(queueId);
    boolean isQueueRecovered = replicationQueueInfo.isQueueRecovered();
    ReplicationSourceInterface src;
    try {
      String defaultReplicationSourceImpl =
          isQueueRecovered ? RecoveredReplicationSource.class.getCanonicalName()
              : ReplicationSource.class.getCanonicalName();
      Class<?> c = Class.forName(
        conf.get("replication.replicationsource.implementation", defaultReplicationSourceImpl));
      src = c.asSubclass(ReplicationSourceInterface.class).getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      LOG.warn("Passed replication source implementation throws errors, "
          + "defaulting to ReplicationSource",
        e);
      src = isQueueRecovered ? new RecoveredReplicationSource() : new ReplicationSource();
    }
    return src;
  }

};