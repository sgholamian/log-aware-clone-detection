//,temp,ReplicationLogCleaner.java,103,112,temp,ReplicationLogCleaner.java,91,101
//,3
public class xxx {
  @Override
  public void setConf(Configuration config) {
    // Make my own Configuration.  Then I'll have my own connection to zk that
    // I can close myself when comes time.
    Configuration conf = new Configuration(config);
    try {
      setConf(conf, new ZKWatcher(conf, "replicationLogCleaner", null));
    } catch (IOException e) {
      LOG.error("Error while configuring " + this.getClass().getName(), e);
    }
  }

};