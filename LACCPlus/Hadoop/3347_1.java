//,temp,RMStateStore.java,487,506,temp,RMStateStore.java,436,455
//,2
public class xxx {
    @Override
    public RMStateStoreState transition(RMStateStore store,
        RMStateStoreEvent event) {
      if (!(event instanceof RMStateStoreRMDTMasterKeyEvent)) {
        // should never happen
        LOG.error("Illegal event type: " + event.getClass());
        return RMStateStoreState.ACTIVE;
      }
      boolean isFenced = false;
      RMStateStoreRMDTMasterKeyEvent dtEvent =
          (RMStateStoreRMDTMasterKeyEvent) event;
      try {
        LOG.info("Storing RMDTMasterKey.");
        store.storeRMDTMasterKeyState(dtEvent.getDelegationKey());
      } catch (Exception e) {
        LOG.error("Error While Storing RMDTMasterKey.", e);
        isFenced = store.notifyStoreOperationFailedInternal(e);
      }
      return finalState(isFenced);
    }

};