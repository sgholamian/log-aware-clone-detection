//,temp,BaseNMTokenSecretManager.java,100,109,temp,BaseNMTokenSecretManager.java,72,87
//,3
public class xxx {
  protected byte[] retrivePasswordInternal(NMTokenIdentifier identifier,
      MasterKeyData masterKey) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("creating password for "
          + identifier.getApplicationAttemptId() + " for user "
          + identifier.getApplicationSubmitter() + " to run on NM "
          + identifier.getNodeId());
    }
    return createPassword(identifier.getBytes(), masterKey.getSecretKey());
  }

};