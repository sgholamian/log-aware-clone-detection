//,temp,cbo_rp_TestJdbcDriver2.java,1419,1453,temp,TestJdbcDriver2.java,1604,1639
//,3
public class xxx {
  @Test
  public void testResultSetColumnNameCaseInsensitive() throws SQLException {
    Statement stmt = con.createStatement();
    ResultSet res;

    res = stmt.executeQuery("select c1 from " + dataTypeTableName + " limit 1");
    try {
      int count = 0;
      while (res.next()) {
        res.findColumn("c1");
        res.findColumn("C1");
        count++;
      }
      assertEquals(count, 1);
    } catch (Exception e) {
      String msg = "Unexpected exception: " + e;
      LOG.info(msg, e);
      fail(msg);
    }

    res = stmt.executeQuery("select c1 C1 from " + dataTypeTableName + " limit 1");
    try {
      int count = 0;
      while (res.next()) {
        res.findColumn("c1");
        res.findColumn("C1");
        count++;
      }
      assertEquals(count, 1);
    } catch (Exception e) {
      String msg = "Unexpected exception: " + e;
      LOG.info(msg, e);
      fail(msg);
    }
  }

};