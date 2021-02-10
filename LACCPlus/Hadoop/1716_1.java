//,temp,JHSDelegationTokenSecretManager.java,99,110,temp,TimelineDelegationTokenSecretManagerService.java,182,195
//,3
public class xxx {
  @Override
  protected void storeNewToken(MRDelegationTokenIdentifier tokenId,
      long renewDate) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Storing token " + tokenId.getSequenceNumber());
    }
    try {
      store.storeToken(tokenId, renewDate);
    } catch (IOException e) {
      LOG.error("Unable to store token " + tokenId.getSequenceNumber(), e);
    }
  }

};