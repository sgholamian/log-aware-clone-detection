//,temp,MemoryRMStateStore.java,246,261,temp,MemoryRMStateStore.java,229,244
//,3
public class xxx {
  @Override
  protected synchronized void updateReservationState(
      ReservationAllocationStateProto reservationAllocation, String planName,
      String reservationIdName) throws Exception {
    LOG.info("Updating reservationallocation for " + reservationIdName + " " +
            "for plan " + planName);
    Map<ReservationId, ReservationAllocationStateProto> planState =
        state.getReservationState().get(planName);
    if (planState == null) {
      throw new YarnRuntimeException("State for plan " + planName + " does " +
          "not exist");
    }
    ReservationId reservationId =
        ReservationId.parseReservationId(reservationIdName);
    planState.put(reservationId, reservationAllocation);
  }

};