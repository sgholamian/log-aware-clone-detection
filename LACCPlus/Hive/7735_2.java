//,temp,TxnHandler.java,3001,3027,temp,TxnHandler.java,2280,2315
//,3
public class xxx {
  @Override
  @RetrySemantics.Idempotent
  public void addWriteNotificationLog(AcidWriteEvent acidWriteEvent)
          throws MetaException {
    Connection dbConn = null;
    try {
      try {
        //Idempotent case is handled by notify Event
        lockInternal();
        dbConn = getDbConn(Connection.TRANSACTION_READ_COMMITTED);
        MetaStoreListenerNotifier.notifyEventWithDirectSql(transactionalListeners,
                EventMessage.EventType.ACID_WRITE, acidWriteEvent, dbConn, sqlGenerator);
        LOG.debug("Going to commit");
        dbConn.commit();
        return;
      } catch (SQLException e) {
        LOG.debug("Going to rollback");
        rollbackDBConn(dbConn);
        if (isDuplicateKeyError(e)) {
          // in case of key duplicate error, retry as it might be because of race condition
          if (waitForRetry("addWriteNotificationLog(" + acidWriteEvent + ")", e.getMessage())) {
            throw new RetryException();
          }
          retryNum = 0;
          throw new MetaException(e.getMessage());
        }
        checkRetryable(dbConn, e, "addWriteNotificationLog(" + acidWriteEvent + ")");
        throw new MetaException("Unable to add write notification event " + StringUtils.stringifyException(e));
      } finally{
        closeDbConn(dbConn);
        unlockInternal();
      }
    } catch (RetryException e) {
      addWriteNotificationLog(acidWriteEvent);
    }
  }

};