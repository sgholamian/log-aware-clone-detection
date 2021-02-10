//,temp,RMStateStore.java,487,506,temp,RMStateStore.java,436,455
//,2
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
        LOG.info("Removing RMDelegationToken and SequenceNumber");
        store.removeRMDelegationTokenState(dtEvent.getRmDTIdentifier());
      } catch (Exception e) {
        LOG.error("Error While Removing RMDelegationToken and SequenceNumber ",
            e);
        isFenced = store.notifyStoreOperationFailedInternal(e);
      }
      return finalState(isFenced);
    }

};