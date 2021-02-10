//,temp,LeveldbRMStateStore.java,251,290,temp,NMLeveldbStateStoreService.java,857,893
//,3
public class xxx {
  private void loadReservationState(RMState rmState) throws IOException {
    int numReservations = 0;
    LeveldbIterator iter = null;
    try {
      iter = new LeveldbIterator(db);
      iter.seek(bytes(RM_RESERVATION_KEY_PREFIX));
      while (iter.hasNext()) {
        Entry<byte[],byte[]> entry = iter.next();
        String key = asString(entry.getKey());

        String planReservationString =
            key.substring(RM_RESERVATION_KEY_PREFIX.length());
        String[] parts = planReservationString.split(SEPARATOR);
        if (parts.length != 2) {
          LOG.warn("Incorrect reservation state key " + key);
          continue;
        }
        String planName = parts[0];
        String reservationName = parts[1];
        ReservationAllocationStateProto allocationState =
            ReservationAllocationStateProto.parseFrom(entry.getValue());
        if (!rmState.getReservationState().containsKey(planName)) {
          rmState.getReservationState().put(planName,
              new HashMap<ReservationId, ReservationAllocationStateProto>());
        }
        ReservationId reservationId =
            ReservationId.parseReservationId(reservationName);
        rmState.getReservationState().get(planName).put(reservationId,
            allocationState);
        numReservations++;
      }
    } catch (DBException e) {
      throw new IOException(e);
    } finally {
      if (iter != null) {
        iter.close();
      }
    }
    LOG.info("Recovered " + numReservations + " reservations");
  }

};