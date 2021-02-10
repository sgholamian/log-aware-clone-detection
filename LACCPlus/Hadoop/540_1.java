//,temp,RMStateStore.java,279,301,temp,RMStateStore.java,252,274
//,2
public class xxx {
    @Override
    public void transition(RMStateStore store, RMStateStoreEvent event) {
      if (!(event instanceof RMStateUpdateAppAttemptEvent)) {
        // should never happen
        LOG.error("Illegal event type: " + event.getClass());
        return;
      }
      ApplicationAttemptStateData attemptState =
          ((RMStateUpdateAppAttemptEvent) event).getAppAttemptState();
      try {
        if (LOG.isDebugEnabled()) {
          LOG.debug("Updating info for attempt: " + attemptState.getAttemptId());
        }
        store.updateApplicationAttemptStateInternal(attemptState.getAttemptId(),
            attemptState);
        store.notifyApplicationAttempt(new RMAppAttemptEvent
               (attemptState.getAttemptId(),
               RMAppAttemptEventType.ATTEMPT_UPDATE_SAVED));
      } catch (Exception e) {
        LOG.error("Error updating appAttempt: " + attemptState.getAttemptId(), e);
        store.notifyStoreOperationFailed(e);
      }
    };

};