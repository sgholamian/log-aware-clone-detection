//,temp,JHSDelegationTokenSecretManager.java,125,136,temp,TimelineDelegationTokenSecretManagerService.java,212,225
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