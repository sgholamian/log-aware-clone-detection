//,temp,MemoryRMStateStore.java,260,278,temp,MemoryRMStateStore.java,243,258
//,3
public class xxx {
  @Override
  protected synchronized void storeReservationState(
      ReservationAllocationStateProto reservationAllocation, String planName,
      String reservationIdName) throws Exception {
    LOG.info("Storing reservationallocation for " + reservationIdName + " " +
            "for plan " + planName);
    Map<ReservationId, ReservationAllocationStateProto> planState =
        state.getReservationState().get(planName);
    if (planState == null) {
      planState = new HashMap<>();
      state.getReservationState().put(planName, planState);
    }
    ReservationId reservationId =
        ReservationId.parseReservationId(reservationIdName);
    planState.put(reservationId, reservationAllocation);
  }

};