//,temp,Utilities.java,3201,3224,temp,Utilities.java,3118,3144
//,3
public class xxx {
  public static <T> T executeWithRetry(SQLCommand<T> cmd, PreparedStatement stmt,
      long baseWindow, int maxRetries)  throws SQLException {

    T result = null;

    // retry with # of maxRetries before throwing exception
    for (int failures = 0; ; failures++) {
      try {
        result = cmd.run(stmt);
        return result;
      } catch (SQLTransientException e) {
        LOG.warn("Failure and retry # {}", failures, e);
        if (failures >= maxRetries) {
          throw e;
        }
        long waitTime = getRandomWaitTime(baseWindow, failures,
            ThreadLocalRandom.current());
        try {
          Thread.sleep(waitTime);
        } catch (InterruptedException iex) {
         }
      } catch (SQLException e) {
        // throw other types of SQLExceptions (SQLNonTransientException / SQLRecoverableException)
        throw e;
      }
    }
  }

};