//,temp,LeveldbRMStateStore.java,634,654,temp,NMLeveldbStateStoreService.java,587,608
//,3
public class xxx {
  @Override
  protected void removeReservationState(String planName,
      String reservationIdName) throws Exception {
    try {
      WriteBatch batch = db.createWriteBatch();
      try {
        String reservationKey =
            getReservationNodeKey(planName, reservationIdName);
        batch.delete(bytes(reservationKey));
        if (LOG.isDebugEnabled()) {
          LOG.debug("Removing state for reservation " + reservationIdName
              + " plan " + planName + " at " + reservationKey);
        }
        db.write(batch);
      } finally {
        batch.close();
      }
    } catch (DBException e) {
      throw new IOException(e);
    }
  }

};