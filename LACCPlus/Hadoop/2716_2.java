//,temp,JHSDelegationTokenSecretManager.java,99,110,temp,TimelineV1DelegationTokenSecretManagerService.java,183,196
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