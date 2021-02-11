//,temp,RMStateStore.java,486,505,temp,RMStateStore.java,461,481
//,3
public class xxx {
    @Override
    public void transition(RMStateStore store, RMStateStoreEvent event) {
      if (!(event instanceof RMStateStoreStoreReservationEvent)) {
        // should never happen
        LOG.error("Illegal event type: " + event.getClass());
        return;
      }
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
        store.notifyStoreOperationFailed(e);
      }
    }

};