//,temp,JHSDelegationTokenSecretManager.java,112,123,temp,TimelineDelegationTokenSecretManagerService.java,197,210
//,3
public class xxx {
    @Override
    protected void removeStoredToken(TimelineDelegationTokenIdentifier tokenId)
        throws IOException {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Storing token " + tokenId.getSequenceNumber());
      }
      try {
        if (stateStore != null) {
          stateStore.removeToken(tokenId);
        }
      } catch (IOException e) {
        LOG.error("Unable to remove token " + tokenId.getSequenceNumber(), e);
      }
    }

};