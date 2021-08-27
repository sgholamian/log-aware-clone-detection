//,temp,HplSqlOperation.java,84,114,temp,SQLOperation.java,221,256
//,3
public class xxx {
  private void interpret() throws HiveSQLException {
    try {
      OperationState opState = getState();
      // Operation may have been cancelled by another thread
      if (opState.isTerminal()) {
        log.info("Not running the query. Operation is already in terminal state: " + opState
                + ", perhaps cancelled due to query timeout or by another thread.");
        return;
      }
      setState(OperationState.RUNNING);
      Arguments args = new Arguments();
      args.parse(new String[]{"-e", statement});
      exec.parseAndEval(args);
      setState(OperationState.FINISHED);
    } catch (Throwable e) {
      if (getState().isTerminal()) {
        log.warn("Ignore exception in terminal state: {}", getState(), e);
        return;
      }
      setState(OperationState.ERROR);
      if (e instanceof HiveSQLException) {
        throw (HiveSQLException) e;
      } else if (e instanceof OutOfMemoryError) {
        throw (OutOfMemoryError) e;
      } else {
        throw new HiveSQLException("Error running HPL/SQL operation", e);
      }
    } finally {
      exec.printExceptions();
    }
  }

};