//,temp,JHSDelegationTokenSecretManager.java,138,147,temp,TimelineDelegationTokenSecretManagerService.java,227,237
//,3
public class xxx {
  public void recover(HistoryServerState state) throws IOException {
    LOG.info("Recovering " + getClass().getSimpleName());
    for (DelegationKey key : state.tokenMasterKeyState) {
      addKey(key);
    }
    for (Entry<MRDelegationTokenIdentifier, Long> entry :
        state.tokenState.entrySet()) {
      addPersistedDelegationToken(entry.getKey(), entry.getValue());
    }
  }

};