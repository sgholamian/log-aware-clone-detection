//,temp,RMDelegationTokenSecretManager.java,133,147,temp,RMDelegationTokenSecretManager.java,116,131
//,3
public class xxx {
  @Override
  protected void updateStoredToken(RMDelegationTokenIdentifier id,
      long renewDate) {
    try {
      LOG.info("updating RMDelegation token with sequence number: "
          + id.getSequenceNumber());
      rm.getRMContext().getStateStore().updateRMDelegationToken(id, renewDate);
    } catch (Exception e) {
      if (!shouldIgnoreException(e)) {
        LOG.error("Error in updating persisted RMDelegationToken"
            + " with sequence number: " + id.getSequenceNumber());
        ExitUtil.terminate(1, e);
      }
    }
  }

};