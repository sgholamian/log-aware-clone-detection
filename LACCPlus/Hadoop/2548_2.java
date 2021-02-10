//,temp,MemoryRMStateStore.java,234,241,temp,MemoryRMStateStore.java,196,204
//,3
public class xxx {
  @Override
  public synchronized void removeRMDelegationTokenState(
      RMDelegationTokenIdentifier rmDTIdentifier) throws Exception{
    Map<RMDelegationTokenIdentifier, Long> rmDTState =
        state.rmSecretManagerState.getTokenState();
    rmDTState.remove(rmDTIdentifier);
    LOG.info("Remove RMDT with sequence number "
        + rmDTIdentifier.getSequenceNumber());
  }

};