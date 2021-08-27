//,temp,TestJdbcWithServiceDiscovery.java,301,324,temp,TestWMMetricsWithTrigger.java,153,177
//,3
public class xxx {
  void runQueryWithTrigger(int queryTimeoutSecs) throws Exception {
    LOG.info("Starting test");
    String query = "select sleep(t1.int_col + t2.int_col, 500), t1.value from " + tableName + " t1 join " + tableName
        + " t2 on t1.int_col>=t2.int_col";
    long start = System.currentTimeMillis();
    Connection conn =
        BaseJdbcWithMiniLlap.getConnection(miniHS2.getJdbcURL(testDbName), System.getProperty("user.name"), "bar");
    final Statement selStmt = conn.createStatement();
    Throwable throwable = null;
    try {
      if (queryTimeoutSecs > 0) {
        selStmt.setQueryTimeout(queryTimeoutSecs);
      }
      selStmt.execute(query);
    } catch (SQLException e) {
      throwable = e;
    }
    selStmt.close();
    assertNotNull("Expected non-null throwable", throwable);
    assertEquals(SQLException.class, throwable.getClass());
    assertTrue("Query was killed due to " + throwable.getMessage() + " and not because of trigger violation",
        throwable.getMessage().contains("violated"));
    long end = System.currentTimeMillis();
    LOG.info("time taken: {} ms", (end - start));
  }

};