//,temp,FileSystemRMStateStore.java,855,866,temp,FileSystemRMStateStore.java,553,564
//,3
public class xxx {
  @Override
  protected void storeReservationState(
      ReservationAllocationStateProto reservationAllocation, String planName,
      String reservationIdName) throws Exception {
    Path planCreatePath = getNodePath(reservationRoot, planName);
    mkdirsWithRetries(planCreatePath);
    Path reservationPath = getNodePath(planCreatePath, reservationIdName);
    LOG.info("Storing state for reservation " + reservationIdName + " from " +
        "plan " + planName + " at path " + reservationPath);
    byte[] reservationData = reservationAllocation.toByteArray();
    writeFileWithRetries(reservationPath, reservationData, true);
  }

};