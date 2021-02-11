//,temp,AMRMProxyTokenSecretManager.java,130,139,temp,NMTokenSecretManagerInRM.java,141,152
//,3
public class xxx {
  public void applicationMasterFinished(ApplicationAttemptId appAttemptId) {
    this.writeLock.lock();
    try {
      LOG.info("Application finished, removing password for "
          + appAttemptId);
      this.appAttemptSet.remove(appAttemptId);
    } finally {
      this.writeLock.unlock();
    }
  }

};