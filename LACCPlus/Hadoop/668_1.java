//,temp,JHSDelegationTokenSecretManager.java,112,123,temp,TimelineDelegationTokenSecretManagerService.java,197,210
//,3
public class xxx {
  @Override
  protected void removeStoredToken(MRDelegationTokenIdentifier tokenId)
      throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Storing token " + tokenId.getSequenceNumber());
    }
    try {
      store.removeToken(tokenId);
    } catch (IOException e) {
      LOG.error("Unable to remove token " + tokenId.getSequenceNumber(), e);
    }
  }

};