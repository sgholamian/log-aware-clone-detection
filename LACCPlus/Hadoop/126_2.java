//,temp,RMStateStore.java,229,247,temp,RMStateStore.java,204,224
//,3
public class xxx {
    @Override
    public void transition(RMStateStore store, RMStateStoreEvent event) {
      if (!(event instanceof RMStateUpdateAppEvent)) {
        // should never happen
        LOG.error("Illegal event type: " + event.getClass());
        return;
      }
      ApplicationStateData appState =
          ((RMStateUpdateAppEvent) event).getAppState();
      ApplicationId appId =
          appState.getApplicationSubmissionContext().getApplicationId();
      LOG.info("Updating info for app: " + appId);
      try {
        store.updateApplicationStateInternal(appId, appState);
        store.notifyApplication(new RMAppEvent(appId,
               RMAppEventType.APP_UPDATE_SAVED));
      } catch (Exception e) {
        LOG.error("Error updating app: " + appId, e);
        store.notifyStoreOperationFailed(e);
      }
    };

};