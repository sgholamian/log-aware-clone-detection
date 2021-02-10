//,temp,ActiveStandbyElector.java,794,812,temp,ActiveStandbyElector.java,777,792
//,3
public class xxx {
  private void createConnection() throws IOException, KeeperException {
    if (zkClient != null) {
      try {
        zkClient.close();
      } catch (InterruptedException e) {
        throw new IOException("Interrupted while closing ZK",
            e);
      }
      zkClient = null;
      watcher = null;
    }
    zkClient = getNewZooKeeper();
    if (LOG.isDebugEnabled()) {
      LOG.debug("Created new connection for " + this);
    }
  }

};