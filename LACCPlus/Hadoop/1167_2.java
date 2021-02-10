//,temp,BaseContainerTokenSecretManager.java,87,101,temp,BaseNMTokenSecretManager.java,72,87
//,3
public class xxx {
  @Override
  protected byte[] createPassword(NMTokenIdentifier identifier) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("creating password for "
          + identifier.getApplicationAttemptId() + " for user "
          + identifier.getApplicationSubmitter() + " to run on NM "
          + identifier.getNodeId());
    }
    readLock.lock();
    try {
      return createPassword(identifier.getBytes(),
          currentMasterKey.getSecretKey());
    } finally {
      readLock.unlock();
    }
  }

};