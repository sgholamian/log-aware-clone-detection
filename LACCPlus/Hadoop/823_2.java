//,temp,LeveldbRMStateStore.java,634,654,temp,LeveldbRMStateStore.java,604,624
//,3
public class xxx {
  @Override
  protected void storeReservationState(
      ReservationAllocationStateProto reservationAllocation, String planName,
      String reservationIdName) throws Exception {
    try {
      WriteBatch batch = db.createWriteBatch();
      try {
        String key = getReservationNodeKey(planName, reservationIdName);
        if (LOG.isDebugEnabled()) {
          LOG.debug("Storing state for reservation " + reservationIdName
              + " plan " + planName + " at " + key);
        }
        batch.put(bytes(key), reservationAllocation.toByteArray());
        db.write(batch);
      } finally {
        batch.close();
      }
    } catch (DBException e) {
      throw new IOException(e);
    }
  }

};