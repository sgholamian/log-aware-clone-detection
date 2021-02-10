//,temp,LeaseRenewer.java,392,407,temp,AbstractDelegationTokenSecretManager.java,617,633
//,3
public class xxx {
  public void interruptAndJoin() throws InterruptedException {
    Daemon daemonCopy = null;
    synchronized (this) {
      if (isRunning()) {
        daemon.interrupt();
        daemonCopy = daemon;
      }
    }

    if (daemonCopy != null) {
      if(LOG.isDebugEnabled()) {
        LOG.debug("Wait for lease checker to terminate");
      }
      daemonCopy.join();
    }
  }

};