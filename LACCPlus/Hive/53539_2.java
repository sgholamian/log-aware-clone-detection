//,temp,HplSqlOperation.java,84,114,temp,SQLOperation.java,221,256
//,3
public class xxx {
  private void runQuery() throws HiveSQLException {
    try {
      OperationState opState = getState();
      // Operation may have been cancelled by another thread
      if (opState.isTerminal()) {
        log.info("Not running the query. Operation is already in terminal state: " + opState
            + ", perhaps cancelled due to query timeout or by another thread.");
        return;
      }
      // In Hive server mode, we are not able to retry in the FetchTask
      // case, when calling fetch queries since execute() has returned.
      // For now, we disable the test attempts.
      driver.run();
    } catch (Throwable e) {
      /**
       * If the operation was cancelled by another thread, or the execution timed out, Driver#run
       * may return a non-zero response code. We will simply return if the operation state is
       * CANCELED, TIMEDOUT, CLOSED or FINISHED, otherwise throw an exception
       */
      if (getState().isTerminal()) {
        log.warn("Ignore exception in terminal state: {}", getState(), e);
        return;
      }
      setState(OperationState.ERROR);
      if (e instanceof CommandProcessorException) {
        throw toSQLException("Error while compiling statement", (CommandProcessorException)e);
      } else if (e instanceof HiveSQLException) {
        throw (HiveSQLException) e;
      } else if (e instanceof OutOfMemoryError) {
        throw (OutOfMemoryError) e;
      } else {
        throw new HiveSQLException("Error running query", e);
      }
    }
    setState(OperationState.FINISHED);
  }

};