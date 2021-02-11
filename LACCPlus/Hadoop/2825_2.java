//,temp,RMStateStore.java,562,585,temp,RMStateStore.java,410,430
//,3
public class xxx {
    @Override
    public RMStateStoreState transition(RMStateStore store,
        RMStateStoreEvent event) {
      if (!(event instanceof RMStateStoreRMDTEvent)) {
        // should never happen
        LOG.error("Illegal event type: " + event.getClass());
        return RMStateStoreState.ACTIVE;
      }
      boolean isFenced = false;
      RMStateStoreRMDTEvent dtEvent = (RMStateStoreRMDTEvent) event;
      try {
        LOG.info("Storing RMDelegationToken and SequenceNumber");
        store.storeRMDelegationTokenState(
            dtEvent.getRmDTIdentifier(), dtEvent.getRenewDate());
      } catch (Exception e) {
        LOG.error("Error While Storing RMDelegationToken and SequenceNumber ",
            e);
        isFenced = store.notifyStoreOperationFailedInternal(e);
      }
      return finalState(isFenced);
    }

};