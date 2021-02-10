//,temp,RMStateStore.java,562,585,temp,RMStateStore.java,321,342
//,3
public class xxx {
    @Override
    public RMStateStoreState transition(RMStateStore store,
        RMStateStoreEvent event) {
      if (!(event instanceof RMStateStoreRemoveAppEvent)) {
        // should never happen
        LOG.error("Illegal event type: " + event.getClass());
        return RMStateStoreState.ACTIVE;
      }
      boolean isFenced = false;
      ApplicationStateData appState =
          ((RMStateStoreRemoveAppEvent) event).getAppState();
      ApplicationId appId =
          appState.getApplicationSubmissionContext().getApplicationId();
      LOG.info("Removing info for app: " + appId);
      try {
        store.removeApplicationStateInternal(appState);
      } catch (Exception e) {
        LOG.error("Error removing app: " + appId, e);
        isFenced = store.notifyStoreOperationFailedInternal(e);
      }
      return finalState(isFenced);
    };

};