//,temp,ActiveStandbyElector.java,794,812,temp,ActiveStandbyElector.java,777,792
//,3
public class xxx {
  @InterfaceAudience.Private
  public synchronized void terminateConnection() {
    if (zkClient == null) {
      return;
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("Terminating ZK connection for " + this);
    }
    ZooKeeper tempZk = zkClient;
    zkClient = null;
    watcher = null;
    try {
      tempZk.close();
    } catch(InterruptedException e) {
      LOG.warn(e);
    }
    zkConnectionState = ConnectionState.TERMINATED;
    wantToBeInElection = false;
  }

};