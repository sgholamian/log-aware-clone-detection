//,temp,RMDelegationTokenSecretManager.java,120,132,temp,RMDelegationTokenSecretManager.java,106,118
//,2
public class xxx {
  @Override
  protected void updateStoredToken(RMDelegationTokenIdentifier id,
      long renewDate) {
    try {
      LOG.info("updating RMDelegation token with sequence number: "
          + id.getSequenceNumber());
      rmContext.getStateStore().updateRMDelegationToken(id, renewDate);
    } catch (Exception e) {
      LOG.error("Error in updating persisted RMDelegationToken" +
                " with sequence number: " + id.getSequenceNumber());
      ExitUtil.terminate(1, e);
    }
  }

};