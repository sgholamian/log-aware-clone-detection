//,temp,FileSystemRMStateStore.java,880,888,temp,FileSystemRMStateStore.java,502,509
//,3
public class xxx {
  @Override
  protected void removeReservationState(
      String planName, String reservationIdName) throws Exception {
    Path planCreatePath = getNodePath(reservationRoot, planName);
    Path reservationPath = getNodePath(planCreatePath, reservationIdName);
    LOG.info("Removing state for reservation " + reservationIdName + " from " +
        "plan " + planName + " at path " + reservationPath);
    deleteFileWithRetries(reservationPath);
  }

};