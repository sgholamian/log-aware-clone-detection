//,temp,RMDelegationTokenSecretManager.java,149,164,temp,RMDelegationTokenSecretManager.java,133,147
//,3
public class xxx {
  @Override
  protected void removeStoredToken(RMDelegationTokenIdentifier ident)
      throws IOException {
    try {
      LOG.info("removing RMDelegation token with sequence number: "
          + ident.getSequenceNumber());
      rm.getRMContext().getStateStore().removeRMDelegationToken(ident);
    } catch (Exception e) {
      if (!shouldIgnoreException(e)) {
        LOG.error(
            "Error in removing RMDelegationToken with sequence number: "
                + ident.getSequenceNumber());
        ExitUtil.terminate(1, e);
      }
    }
  }

};