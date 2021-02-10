//,temp,RMStateStore.java,229,247,temp,RMStateStore.java,179,199
//,3
public class xxx {
    @Override
    public void transition(RMStateStore store, RMStateStoreEvent event) {
      if (!(event instanceof RMStateStoreAppEvent)) {
        // should never happen
        LOG.error("Illegal event type: " + event.getClass());
        return;
      }
      ApplicationStateData appState =
          ((RMStateStoreAppEvent) event).getAppState();
      ApplicationId appId =
          appState.getApplicationSubmissionContext().getApplicationId();
      LOG.info("Storing info for app: " + appId);
      try {
        store.storeApplicationStateInternal(appId, appState);
        store.notifyApplication(new RMAppEvent(appId,
               RMAppEventType.APP_NEW_SAVED));
      } catch (Exception e) {
        LOG.error("Error storing app: " + appId, e);
        store.notifyStoreOperationFailed(e);
      }
    };

};