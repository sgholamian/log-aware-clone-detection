//,temp,RMStateStore.java,246,285,temp,RMStateStore.java,210,239
//,3
public class xxx {
    @Override
    public RMStateStoreState transition(RMStateStore store,
        RMStateStoreEvent event) {
      if (!(event instanceof RMStateStoreAppEvent)) {
        // should never happen
        LOG.error("Illegal event type: " + event.getClass());
        return RMStateStoreState.ACTIVE;
      }
      boolean isFenced = false;
      ApplicationStateData appState =
          ((RMStateStoreAppEvent) event).getAppState();
      ApplicationId appId =
          appState.getApplicationSubmissionContext().getApplicationId();
      LOG.info("Storing info for app: " + appId);
      try {
        store.storeApplicationStateInternal(appId, appState);
        store.notifyApplication(
            new RMAppEvent(appId, RMAppEventType.APP_NEW_SAVED));
      } catch (Exception e) {
        LOG.error("Error storing app: " + appId, e);
        if (e instanceof StoreLimitException) {
          store.notifyApplication(
              new RMAppEvent(appId, RMAppEventType.APP_SAVE_FAILED,
                  e.getMessage()));
        } else {
          isFenced = store.notifyStoreOperationFailedInternal(e);
        }
      }
      return finalState(isFenced);
    };

};