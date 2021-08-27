//,temp,Utilities.java,3201,3224,temp,Utilities.java,3118,3144
//,3
public class xxx {
  public static PreparedStatement prepareWithRetry(Connection conn, String stmt,
      long waitWindow, int maxRetries) throws SQLException {

    // retry with # of maxRetries before throwing exception
    for (int failures = 0; ; failures++) {
      try {
        return conn.prepareStatement(stmt);
      } catch (SQLTransientException e) {
        if (failures >= maxRetries) {
          LOG.error("Error preparing JDBC Statement {}", stmt, e);
          throw e;
        }
        long waitTime = Utilities.getRandomWaitTime(waitWindow, failures,
            ThreadLocalRandom.current());
        try {
          Thread.sleep(waitTime);
        } catch (InterruptedException e1) {
        }
      } catch (SQLException e) {
        // just throw other types (SQLNonTransientException / SQLRecoverableException)
        throw e;
      }
    }
  }

};