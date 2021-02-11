//,temp,RMStateStore.java,486,505,temp,RMStateStore.java,436,456
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
        LOG.info("Storing reservation allocation." + reservationEvent
                .getReservationIdName());
        store.storeReservationState(
            reservationEvent.getReservationAllocation(),
            reservationEvent.getPlanName(),
            reservationEvent.getReservationIdName());
      } catch (Exception e) {
        LOG.error("Error while storing reservation allocation.", e);
        store.notifyStoreOperationFailed(e);
      }
    }

};