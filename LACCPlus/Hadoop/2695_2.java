//,temp,AMRMProxyTokenSecretManager.java,307,320,temp,BaseNMTokenSecretManager.java,72,84
//,3
public class xxx {
  @Override
  protected byte[] createPassword(NMTokenIdentifier identifier) {
    LOG.debug("creating password for {} for user {} to run on NM {}",
        identifier.getApplicationAttemptId(),
        identifier.getApplicationSubmitter(), identifier.getNodeId());
    readLock.lock();
    try {
      return createPassword(identifier.getBytes(),
          currentMasterKey.getSecretKey());
    } finally {
      readLock.unlock();
    }
  }

};