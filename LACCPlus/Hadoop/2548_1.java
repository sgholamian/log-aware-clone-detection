//,temp,MemoryRMStateStore.java,234,241,temp,MemoryRMStateStore.java,196,204
//,3
public class xxx {
  @Override
  public synchronized void removeRMDTMasterKeyState(DelegationKey delegationKey)
      throws Exception {
    LOG.info("Remove RMDT master key with key id: " + delegationKey.getKeyId());
    Set<DelegationKey> rmDTMasterKeyState =
        state.rmSecretManagerState.getMasterKeyState();
    rmDTMasterKeyState.remove(delegationKey);
  }

};