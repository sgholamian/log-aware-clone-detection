//,temp,TxnHandler.java,3001,3027,temp,TxnHandler.java,2280,2315
//,3
public class xxx {
  private LockResponse checkLockWithRetry(Connection dbConn, long extLockId, long txnId, boolean zeroWaitReadEnabled)
    throws NoSuchLockException, TxnAbortedException, MetaException {
    try {
      try {
        lockInternal();
        if(dbConn.isClosed()) {
          //should only get here if retrying this op
          dbConn = getDbConn(Connection.TRANSACTION_READ_COMMITTED);
        }
        return checkLock(dbConn, extLockId, txnId, zeroWaitReadEnabled);
      } catch (SQLException e) {
        LOG.error("checkLock failed for extLockId={}/txnId={}. Exception msg: {}", extLockId, txnId, getMessage(e));
        rollbackDBConn(dbConn);
        checkRetryable(dbConn, e, "checkLockWithRetry(" + extLockId + "," + txnId + ")");
        throw new MetaException("Unable to update transaction database " +
          StringUtils.stringifyException(e));
      } finally {
        unlockInternal();
        closeDbConn(dbConn);
      }
    }
    catch(RetryException e) {
      LOG.debug("Going to retry checkLock for extLockId={}/txnId={} after catching RetryException with message: {}",
              extLockId, txnId, e.getMessage());
      return checkLockWithRetry(dbConn, extLockId, txnId, zeroWaitReadEnabled);
    }
  }

};