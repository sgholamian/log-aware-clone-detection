//,temp,BaseContainerTokenSecretManager.java,87,101,temp,AMRMTokenSecretManager.java,300,313
//,3
public class xxx {
  @Override
  @Private
  protected byte[] createPassword(AMRMTokenIdentifier identifier) {
    this.readLock.lock();
    try {
      ApplicationAttemptId applicationAttemptId =
          identifier.getApplicationAttemptId();
      LOG.info("Creating password for " + applicationAttemptId);
      return createPassword(identifier.getBytes(), getMasterKey()
        .getSecretKey());
    } finally {
      this.readLock.unlock();
    }
  }

};