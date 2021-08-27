//,temp,TxnHandler.java,4970,4990,temp,TxnHandler.java,4948,4967
//,3
public class xxx {
  private void heartbeatLock(Connection dbConn, long extLockId)
    throws NoSuchLockException, SQLException, MetaException {
    // If the lock id is 0, then there are no locks in this heartbeat
    if (extLockId == 0) {
      return;
    }
    try (Statement stmt = dbConn.createStatement()) {
      String updateHeartbeatQuery = "UPDATE \"HIVE_LOCKS\" SET \"HL_LAST_HEARTBEAT\" = " +
          getEpochFn(dbProduct) + " WHERE \"HL_LOCK_EXT_ID\" = " + extLockId;
      LOG.debug("Going to execute update <" + updateHeartbeatQuery + ">");
      int rc = stmt.executeUpdate(updateHeartbeatQuery);
      if (rc < 1) {
        LOG.error("Failure to update last heartbeat for extLockId={}.", extLockId);
        dbConn.rollback();
        throw new NoSuchLockException("No such lock: " + JavaUtils.lockIdToString(extLockId));
      }
      LOG.debug("Successfully heartbeated for extLockId={}", extLockId);
      dbConn.commit();
    }
  }

};