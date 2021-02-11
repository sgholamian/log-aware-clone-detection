//,temp,AMRMProxyTokenSecretManager.java,307,320,temp,BaseNMTokenSecretManager.java,72,84
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