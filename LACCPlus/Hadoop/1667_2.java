//,temp,RMDelegationTokenSecretManager.java,134,146,temp,RMDelegationTokenSecretManager.java,106,118
//,3
public class xxx {
  @Override
  protected void storeNewToken(RMDelegationTokenIdentifier identifier,
      long renewDate) {
    try {
      LOG.info("storing RMDelegation token with sequence number: "
          + identifier.getSequenceNumber());
      rmContext.getStateStore().storeRMDelegationToken(identifier, renewDate);
    } catch (Exception e) {
      LOG.error("Error in storing RMDelegationToken with sequence number: "
          + identifier.getSequenceNumber());
      ExitUtil.terminate(1, e);
    }
  }

};