//,temp,TestJdbcWithServiceDiscovery.java,183,238,temp,TestJdbcWithMiniLlapArrow.java,326,399
//,3
public class xxx {
  private void testKillQueryInternal(String user, String killUser, boolean useTag, final
      ExceptionHolder stmtHolder, final ExceptionHolder tKillHolder) throws Exception {
    Connection con1 = BaseJdbcWithMiniLlap.getConnection(miniHS2.getJdbcURL(testDbName),
            user, "bar");
    Connection con2 = BaseJdbcWithMiniLlap.getConnection(miniHS2.getJdbcURL(testDbName),
            killUser, "bar");

    final Statement stmt2 = con2.createStatement();
    final HiveStatement stmt = (HiveStatement)con1.createStatement();
    final StringBuffer stmtQueryId = new StringBuffer();

    // Thread executing the query
    Thread tExecute = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          System.out.println("Executing query: ");
          stmt.execute("set hive.llap.execution.mode = none");

          if (useTag) {
            stmt.execute("set hive.query.tag = " + tag);
          }
          // The test table has 500 rows, so total query time should be ~ 500*500ms
          stmt.executeAsync("select sleepMsUDF(t1.int_col, 100), t1.int_col, t2.int_col " +
                  "from " + tableName + " t1 join " + tableName + " t2 on t1.int_col = t2.int_col");
          stmtQueryId.append(stmt.getQueryId());
          stmt.getUpdateCount();
        } catch (SQLException e) {
          stmtHolder.throwable = e;
        }
      }
    });

    tExecute.start();

    // wait for other thread to create the stmt handle
    int count = 0;
    while (++count <= 10) {
      try {
        tKillHolder.throwable = null;
        Thread.sleep(2000);
        String queryId;
        if (useTag) {
          queryId = tag;
        } else {
          if (stmtQueryId.length() != 0) {
            queryId = stmtQueryId.toString();
          } else {
            continue;
          }
        }
        System.out.println("Killing query: " + queryId);
        if (killUser.equals(System.getProperty("user.name"))) {
          stmt2.execute("set role admin");
        }
        stmt2.execute("kill query '" + queryId + "'");
        stmt2.close();
        break;
      } catch (SQLException e) {
        LOG.warn("Exception when kill query", e);
        tKillHolder.throwable = e;
      }
    }

    tExecute.join();
    try {
      stmt.close();
      con1.close();
      con2.close();
    } catch (Exception e) {
      // ignore error
      LOG.warn("Exception when close stmt and con", e);
    }
  }

};