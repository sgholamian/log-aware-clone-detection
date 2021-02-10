//,temp,MemoryRMStateStore.java,260,278,temp,MemoryRMStateStore.java,243,258
//,3
public class xxx {
  @Override
  protected synchronized void removeReservationState(
      String planName, String reservationIdName) throws Exception {
    LOG.info("Removing reservationallocation " + reservationIdName
              + " for plan " + planName);

    Map<ReservationId, ReservationAllocationStateProto> planState =
        state.getReservationState().get(planName);
    if (planState == null) {
      throw new YarnRuntimeException("State for plan " + planName + " does " +
          "not exist");
    }
    ReservationId reservationId =
        ReservationId.parseReservationId(reservationIdName);
    planState.remove(reservationId);
    if (planState.isEmpty()) {
      state.getReservationState().remove(planName);
    }
  }

};