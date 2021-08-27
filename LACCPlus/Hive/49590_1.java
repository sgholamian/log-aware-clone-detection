//,temp,TestJdbcWithServiceDiscovery.java,183,238,temp,TestJdbcWithMiniLlapArrow.java,326,399
//,3
public class xxx {
  private void executeQueryAndKill(Connection con1, Connection con2, ExceptionHolder tExecuteHolder,
      ExceptionHolder tKillHolder) throws SQLException, InterruptedException {
    final HiveStatement stmt = (HiveStatement) con1.createStatement();
    final Statement stmt2 = con2.createStatement();
    final StringBuffer stmtQueryId = new StringBuffer();

    // Thread executing the query
    Thread tExecute = new Thread(() -> {
      try {
        LOG.info("Executing waiting query.");
        // The test table has 500 rows, so total query time should be ~ 500*500ms
        stmt.executeAsync(
            "select sleepMsUDF(t1.int_col, 1), t1.int_col, t2.int_col " + "from " + TABLE_NAME + " t1 join "
                + TABLE_NAME + " t2 on t1.int_col = t2.int_col");
        stmtQueryId.append(stmt.getQueryId());
        stmt.getUpdateCount();
      } catch (SQLException e) {
        tExecuteHolder.throwable = e;
      }
    });

    tExecute.start();

    // wait for other thread to create the stmt handle
    int count = 0;
    while (count < 10) {
      try {
        Thread.sleep(500);
        String queryId;
        if (stmtQueryId.length() != 0) {
          queryId = stmtQueryId.toString();
        } else {
          count++;
          continue;
        }

        LOG.info("Killing query: " + queryId);
        stmt2.execute("kill query '" + queryId + "'");
        stmt2.close();
        break;
      } catch (SQLException e) {
        LOG.warn("Exception when kill query", e);
        tKillHolder.throwable = e;
        break;
      }
    }

    tExecute.join();
    try {
      stmt.close();
      con1.close();
      con2.close();
    } catch (Exception e) {
      LOG.warn("Exception when close stmt and con", e);
    }
  }

};