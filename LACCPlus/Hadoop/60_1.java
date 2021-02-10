//,temp,FileSystemRMStateStore.java,868,878,temp,FileSystemRMStateStore.java,855,866
//,3
public class xxx {
  @Override
  protected void updateReservationState(
      ReservationAllocationStateProto reservationAllocation, String planName,
      String reservationIdName) throws Exception {
    Path planCreatePath = getNodePath(reservationRoot, planName);
    Path reservationPath = getNodePath(planCreatePath, reservationIdName);
    LOG.info("Updating state for reservation " + reservationIdName + " from " +
        "plan " + planName + " at path " + reservationPath);
    byte[] reservationData = reservationAllocation.toByteArray();
    updateFile(reservationPath, reservationData, true);
  }

};