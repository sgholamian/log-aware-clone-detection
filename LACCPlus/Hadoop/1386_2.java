//,temp,RMDelegationTokenSecretManager.java,175,194,temp,TimelineDelegationTokenSecretManagerService.java,227,237
//,3
public class xxx {
    public void recover(TimelineServiceState state) throws IOException {
      LOG.info("Recovering " + getClass().getSimpleName());
      for (DelegationKey key : state.getTokenMasterKeyState()) {
        addKey(key);
      }
      this.delegationTokenSequenceNumber = state.getLatestSequenceNumber();
      for (Entry<TimelineDelegationTokenIdentifier, Long> entry :
          state.getTokenState().entrySet()) {
        addPersistedDelegationToken(entry.getKey(), entry.getValue());
      }
    }

};