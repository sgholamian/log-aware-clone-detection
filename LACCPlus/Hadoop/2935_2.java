//,temp,RMDelegationTokenSecretManager.java,133,147,temp,RMDelegationTokenSecretManager.java,116,131
//,3
public class xxx {
  @Override
  protected void storeNewToken(RMDelegationTokenIdentifier identifier,
      long renewDate) {
    try {
      LOG.info("storing RMDelegation token with sequence number: "
          + identifier.getSequenceNumber());
      rm.getRMContext().getStateStore().storeRMDelegationToken(identifier,
          renewDate);
    } catch (Exception e) {
      if (!shouldIgnoreException(e)) {
        LOG.error("Error in storing RMDelegationToken with sequence number: "
            + identifier.getSequenceNumber());
        ExitUtil.terminate(1, e);
      }
    }
  }

};