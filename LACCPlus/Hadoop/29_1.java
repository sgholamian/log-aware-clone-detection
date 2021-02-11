//,temp,RMDelegationTokenSecretManager.java,134,146,temp,RMDelegationTokenSecretManager.java,95,104
//,3
public class xxx {
  @Override
  protected void removeStoredToken(RMDelegationTokenIdentifier ident)
      throws IOException {
    try {
      LOG.info("removing RMDelegation token with sequence number: "
          + ident.getSequenceNumber());
      rmContext.getStateStore().removeRMDelegationToken(ident);
    } catch (Exception e) {
      LOG.error("Error in removing RMDelegationToken with sequence number: "
          + ident.getSequenceNumber());
      ExitUtil.terminate(1, e);
    }
  }

};