//,temp,RMStateStore.java,623,643,temp,RMStateStore.java,537,556
//,3
public class xxx {
    @Override
    public RMStateStoreState transition(RMStateStore store,
        RMStateStoreEvent event) {
      if (!(event instanceof RMStateStoreRemoveAppAttemptEvent)) {
        // should never happen
        LOG.error("Illegal event type: " + event.getClass());
        return RMStateStoreState.ACTIVE;
      }
      boolean isFenced = false;
      ApplicationAttemptId attemptId =
          ((RMStateStoreRemoveAppAttemptEvent) event).getApplicationAttemptId();
      ApplicationId appId = attemptId.getApplicationId();
      LOG.info("Removing attempt " + attemptId + " from app: " + appId);
      try {
        store.removeApplicationAttemptInternal(attemptId);
      } catch (Exception e) {
        LOG.error("Error removing attempt: " + attemptId, e);
        isFenced = store.notifyStoreOperationFailedInternal(e);
      }
      return finalState(isFenced);
    }

};