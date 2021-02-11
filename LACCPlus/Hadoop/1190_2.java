//,temp,RMContainerTokenSecretManager.java,138,149,temp,AMRMTokenSecretManager.java,121,129
//,3
public class xxx {
  public void applicationMasterFinished(ApplicationAttemptId appAttemptId) {
    this.writeLock.lock();
    try {
      LOG.info("Application finished, removing password for " + appAttemptId);
      this.appAttemptSet.remove(appAttemptId);
    } finally {
      this.writeLock.unlock();
    }
  }

};