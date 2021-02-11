//,temp,LeaseRenewer.java,364,377,temp,AbstractDelegationTokenSecretManager.java,643,659
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
      LOG.debug("Wait for lease checker to terminate");
      daemonCopy.join();
    }
  }

};