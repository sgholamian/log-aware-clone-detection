//,temp,TimelineV1DelegationTokenSecretManagerService.java,213,223,temp,RMDelegationTokenSecretManager.java,193,212
//,3
public class xxx {
  @Override
  public void recover(RMState rmState) throws Exception {

    LOG.info("recovering RMDelegationTokenSecretManager.");
    // recover RMDTMasterKeys
    for (DelegationKey dtKey : rmState.getRMDTSecretManagerState()
      .getMasterKeyState()) {
      addKey(dtKey);
    }

    // recover RMDelegationTokens
    Map<RMDelegationTokenIdentifier, Long> rmDelegationTokens =
        rmState.getRMDTSecretManagerState().getTokenState();
    this.delegationTokenSequenceNumber =
        rmState.getRMDTSecretManagerState().getDTSequenceNumber();
    for (Map.Entry<RMDelegationTokenIdentifier, Long> entry : rmDelegationTokens
      .entrySet()) {
      addPersistedDelegationToken(entry.getKey(), entry.getValue());
    }
  }

};