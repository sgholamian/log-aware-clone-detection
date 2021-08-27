//,temp,TestJdbcWithServiceDiscovery.java,301,324,temp,TestWMMetricsWithTrigger.java,153,177
//,3
public class xxx {
  @Test
  public void testKillQueryWithRandomId() throws Exception {
    Connection con1 = DriverManager.getConnection(miniHS2directUrl1, System.getProperty("user.name"), "bar");
    ExceptionHolder tKillHolder = new ExceptionHolder();

    Statement stmt = con1.createStatement();
    String queryId = "randomId123";
    try {
      LOG.info("Killing query: " + queryId);
      stmt.execute("kill query '" + queryId + "'");
      stmt.close();
    } catch (SQLException e) {
      LOG.warn("Exception when kill query", e);
      tKillHolder.throwable = e;
    }
    try {
      con1.close();
    } catch (Exception e) {
      LOG.warn("Exception when close stmt and con", e);
    }

    assertNotNull("tCancel", tKillHolder.throwable);
    assertTrue(tKillHolder.throwable.getMessage(), tKillHolder.throwable.getMessage().contains(REMOTE_ERROR_MESSAGE));
  }

};