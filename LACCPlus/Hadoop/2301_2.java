//,temp,TimelineV1DelegationTokenSecretManagerService.java,183,196,temp,TimelineV1DelegationTokenSecretManagerService.java,168,181
//,3
public class xxx {
    @Override
    protected void storeNewToken(TimelineDelegationTokenIdentifier tokenId,
        long renewDate) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Storing token " + tokenId.getSequenceNumber());
      }
      try {
        if (stateStore != null) {
          stateStore.storeToken(tokenId, renewDate);
        }
      } catch (IOException e) {
        LOG.error("Unable to store token " + tokenId.getSequenceNumber(), e);
      }
    }

};