//,temp,RMStateStore.java,393,409,temp,RMStateStore.java,372,388
//,2
public class xxx {
    @Override
    public void transition(RMStateStore store, RMStateStoreEvent event) {
      if (!(event instanceof RMStateStoreRMDTMasterKeyEvent)) {
        // should never happen
        LOG.error("Illegal event type: " + event.getClass());
        return;
      }
      RMStateStoreRMDTMasterKeyEvent dtEvent =
          (RMStateStoreRMDTMasterKeyEvent) event;
      try {
        LOG.info("Removing RMDTMasterKey.");
        store.removeRMDTMasterKeyState(dtEvent.getDelegationKey());
      } catch (Exception e) {
        LOG.error("Error While Removing RMDTMasterKey.", e);
        store.notifyStoreOperationFailed(e);
      }
    }

};