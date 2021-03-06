//,temp,RMStateStore.java,461,481,temp,RMStateStore.java,436,456
//,2
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
        LOG.info("Updating reservation allocation." + reservationEvent
                .getReservationIdName());
        store.updateReservationState(
            reservationEvent.getReservationAllocation(),
            reservationEvent.getPlanName(),
            reservationEvent.getReservationIdName());
      } catch (Exception e) {
        LOG.error("Error while updating reservation allocation.", e);
        store.notifyStoreOperationFailed(e);
      }
    }

};