//,temp,AMRMTokenSecretManager.java,223,233,temp,AMRMTokenSecretManager.java,121,129
//,3
public class xxx {
  public void addPersistedPassword(Token<AMRMTokenIdentifier> token)
      throws IOException {
    this.writeLock.lock();
    try {
      AMRMTokenIdentifier identifier = token.decodeIdentifier();
      LOG.debug("Adding password for " + identifier.getApplicationAttemptId());
      appAttemptSet.add(identifier.getApplicationAttemptId());
    } finally {
      this.writeLock.unlock();
    }
  }

};