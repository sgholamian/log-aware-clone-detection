//,temp,JHSDelegationTokenSecretManager.java,112,123,temp,TimelineV1DelegationTokenSecretManagerService.java,198,211
//,3
public class xxx {
    @Override
    protected void updateStoredToken(TimelineDelegationTokenIdentifier tokenId,
        long renewDate) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Updating token " + tokenId.getSequenceNumber());
      }
      try {
        if (stateStore != null) {
          stateStore.updateToken(tokenId, renewDate);
        }
      } catch (IOException e) {
        LOG.error("Unable to update token " + tokenId.getSequenceNumber(), e);
      }
    }

};