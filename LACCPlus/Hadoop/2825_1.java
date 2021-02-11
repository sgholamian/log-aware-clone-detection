//,temp,RMStateStore.java,562,585,temp,RMStateStore.java,410,430
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
        LOG.info("Storing reservation allocation." + reservationEvent
                .getReservationIdName());
        store.storeReservationState(
            reservationEvent.getReservationAllocation(),
            reservationEvent.getPlanName(),
            reservationEvent.getReservationIdName());
      } catch (Exception e) {
        LOG.error("Error while storing reservation allocation.", e);
        isFenced = store.notifyStoreOperationFailedInternal(e);
      }
      return finalState(isFenced);
    }

};