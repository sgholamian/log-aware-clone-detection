//,temp,RMStateStore.java,246,285,temp,RMStateStore.java,210,239
//,3
public class xxx {
    @Override
    public RMStateStoreState transition(RMStateStore store,
        RMStateStoreEvent event) {
      if (!(event instanceof RMStateUpdateAppEvent)) {
        // should never happen
        LOG.error("Illegal event type: " + event.getClass());
        return RMStateStoreState.ACTIVE;
      }
      boolean isFenced = false;
      ApplicationStateData appState =
          ((RMStateUpdateAppEvent) event).getAppState();
      SettableFuture<Object> result =
          ((RMStateUpdateAppEvent) event).getResult();
      ApplicationId appId =
          appState.getApplicationSubmissionContext().getApplicationId();
      LOG.info("Updating info for app: " + appId);
      try {
        if (isAppStateFinal(appState)) {
          pruneAppState(appState);
        }
        store.updateApplicationStateInternal(appId, appState);
        if (((RMStateUpdateAppEvent) event).isNotifyApplication()) {
          store.notifyApplication(new RMAppEvent(appId,
              RMAppEventType.APP_UPDATE_SAVED));
        }

        if (result != null) {
          result.set(null);
        }

      } catch (Exception e) {
        String msg = "Error updating app: " + appId;
        LOG.error(msg, e);
        isFenced = store.notifyStoreOperationFailedInternal(e);
        if (result != null) {
          result.setException(new YarnException(msg, e));
        }
      }
      return finalState(isFenced);
    }

};