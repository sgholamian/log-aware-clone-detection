//,temp,RMStateStore.java,591,613,temp,RMStateStore.java,321,342
//,3
public class xxx {
    @Override
    public RMStateStoreState transition(RMStateStore store,
        RMStateStoreEvent event) {
      if (!(event instanceof RMStateStoreStoreReservationEvent)) {
        // should never happen
        LOG.error("Illegal event type: " + event.getClass());
        return RMStateStoreState.ACTIVE;
      }
      boolean isFenced = false;
      RMStateStoreStoreReservationEvent reservationEvent =
          (RMStateStoreStoreReservationEvent) event;
      try {
        LOG.info("Removing reservation allocation." + reservationEvent
                .getReservationIdName());
        store.removeReservationState(
            reservationEvent.getPlanName(),
            reservationEvent.getReservationIdName());
      } catch (Exception e) {
        LOG.error("Error while removing reservation allocation.", e);
        isFenced = store.notifyStoreOperationFailedInternal(e);
      }
      return finalState(isFenced);
    }

};