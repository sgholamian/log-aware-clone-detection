//,temp,RMStateStore.java,279,301,temp,RMStateStore.java,252,274
//,2
public class xxx {
    @Override
    public void transition(RMStateStore store, RMStateStoreEvent event) {
      if (!(event instanceof RMStateStoreAppAttemptEvent)) {
        // should never happen
        LOG.error("Illegal event type: " + event.getClass());
        return;
      }
      ApplicationAttemptStateData attemptState =
          ((RMStateStoreAppAttemptEvent) event).getAppAttemptState();
      try {
        if (LOG.isDebugEnabled()) {
          LOG.debug("Storing info for attempt: " + attemptState.getAttemptId());
        }
        store.storeApplicationAttemptStateInternal(attemptState.getAttemptId(),
            attemptState);
        store.notifyApplicationAttempt(new RMAppAttemptEvent
               (attemptState.getAttemptId(),
               RMAppAttemptEventType.ATTEMPT_NEW_SAVED));
      } catch (Exception e) {
        LOG.error("Error storing appAttempt: " + attemptState.getAttemptId(), e);
        store.notifyStoreOperationFailed(e);
      }
    };

};