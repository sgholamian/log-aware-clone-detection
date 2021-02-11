//,temp,RMStateStore.java,229,247,temp,RMStateStore.java,179,199
//,3
public class xxx {
    @Override
    public void transition(RMStateStore store, RMStateStoreEvent event) {
      if (!(event instanceof RMStateStoreRemoveAppEvent)) {
        // should never happen
        LOG.error("Illegal event type: " + event.getClass());
        return;
      }
      ApplicationStateData appState =
          ((RMStateStoreRemoveAppEvent) event).getAppState();
      ApplicationId appId =
          appState.getApplicationSubmissionContext().getApplicationId();
      LOG.info("Removing info for app: " + appId);
      try {
        store.removeApplicationStateInternal(appState);
      } catch (Exception e) {
        LOG.error("Error removing app: " + appId, e);
        store.notifyStoreOperationFailed(e);
      }
    };

};