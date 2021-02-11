//,temp,ReplicationLogCleaner.java,103,112,temp,ReplicationLogCleaner.java,91,101
//,3
public class xxx {
  @VisibleForTesting
  public void setConf(Configuration conf, ZKWatcher zk) {
    super.setConf(conf);
    try {
      this.zkw = zk;
      this.queueStorage = ReplicationStorageFactory.getReplicationQueueStorage(zk, conf);
    } catch (Exception e) {
      LOG.error("Error while configuring " + this.getClass().getName(), e);
    }
  }

};