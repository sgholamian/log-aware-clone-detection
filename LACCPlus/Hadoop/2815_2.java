//,temp,RMStateStore.java,512,531,temp,RMStateStore.java,461,481
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
        LOG.info("Updating RMDelegationToken and SequenceNumber");
        store.updateRMDelegationTokenState(
            dtEvent.getRmDTIdentifier(), dtEvent.getRenewDate());
      } catch (Exception e) {
        LOG.error("Error While Updating RMDelegationToken and SequenceNumber ",
            e);
        isFenced = store.notifyStoreOperationFailedInternal(e);
      }
      return finalState(isFenced);
    }

};