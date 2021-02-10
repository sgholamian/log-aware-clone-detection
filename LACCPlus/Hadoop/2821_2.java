//,temp,RMStateStore.java,623,643,temp,RMStateStore.java,537,556
//,3
public class xxx {
    @Override
    public RMStateStoreState transition(RMStateStore store,
        RMStateStoreEvent event) {
      if (!(event instanceof RMStateStoreAMRMTokenEvent)) {
        // should never happen
        LOG.error("Illegal event type: " + event.getClass());
        return RMStateStoreState.ACTIVE;
      }
      RMStateStoreAMRMTokenEvent amrmEvent = (RMStateStoreAMRMTokenEvent) event;
      boolean isFenced = false;
      try {
        LOG.info("Updating AMRMToken");
        store.storeOrUpdateAMRMTokenSecretManagerState(
            amrmEvent.getAmrmTokenSecretManagerState(), amrmEvent.isUpdate());
      } catch (Exception e) {
        LOG.error("Error storing info for AMRMTokenSecretManager", e);
        isFenced = store.notifyStoreOperationFailedInternal(e);
      }
      return finalState(isFenced);
    }

};