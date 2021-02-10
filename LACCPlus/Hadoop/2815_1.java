//,temp,RMStateStore.java,512,531,temp,RMStateStore.java,461,481
//,3
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
        LOG.info("Removing RMDTMasterKey.");
        store.removeRMDTMasterKeyState(dtEvent.getDelegationKey());
      } catch (Exception e) {
        LOG.error("Error While Removing RMDTMasterKey.", e);
        isFenced = store.notifyStoreOperationFailedInternal(e);
      }
      return finalState(isFenced);
    }

};