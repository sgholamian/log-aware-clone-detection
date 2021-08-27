//,temp,TestHiveMetastoreTransformer.java,661,750,temp,TestHiveMetastoreTransformer.java,588,659
//,3
public class xxx {
  @Test
  public void testTransformerVirtualView() throws Exception {
    try {
      final String dbName = "db1";
      String basetblName = "oldstyleview";
      Map<String, Object> tProps = new HashMap<>();

      String tblName = basetblName;
      tProps.put("DBNAME", dbName);
      tProps.put("TBLNAME", tblName);
      tProps.put("TBLTYPE", TableType.VIRTUAL_VIEW);
      StringBuilder properties = new StringBuilder();
      tProps.put("PROPERTIES", properties.toString());
      Table tbl = createTableWithCapabilities(tProps);

      // retrieve the table
      Table tbl2 = client.getTable(dbName, tblName);
      LOG.info("View=" + tblName + ",Access=" + tbl2.getAccessType());
      assertEquals("Created and retrieved views do not match:" + tbl2.getTableName() + ":" + tblName,
          tbl2.getTableName(), tblName);
      assertEquals("TableType mismatch", TableType.VIRTUAL_VIEW.name(), tbl2.getTableType());
      assertEquals("Table access type does not match expected value:" + tblName,
          0, tbl2.getAccessType()); // old client, AccessType not set

      // table has capabilities
      tblName = "test_view_wc";
      properties = new StringBuilder();
      properties.append(CAPABILITIES_KEY).append("=").append("HIVESQL");
      tProps.put("TBLNAME", tblName);
      tProps.put("PROPERTIES", properties.toString());
      tbl = createTableWithCapabilities(tProps);

      tbl2 = client.getTable(dbName, tblName);
      assertEquals("Table access type does not match expected value:" + tblName,
          0, tbl2.getAccessType()); // old client, no transformation

      setHMSClient("testTransformerVirtualView", (new String[] { "HIVESQL" }));
      tbl2 = client.getTable(dbName, tblName);
      assertEquals("Table access type does not match the expected value:" + tblName,
          ACCESSTYPE_READONLY, tbl2.getAccessType()); // RO accessonly for all views

      setHMSClient("testTransformerVirtualView", (new String[] { "EXTWRITE", "HIVEFULLACIDWRITE" }));
      tbl2 = client.getTable(dbName, tblName);
      assertEquals("Table access type does not match the expected value:" + tblName,
          ACCESSTYPE_NONE, tbl2.getAccessType()); // missing HIVESQL

      resetHMSClient();

      // table does not capabilities but client is newer
      tblName = "test_view_woc";
      properties = new StringBuilder();
      tProps.put("TBLNAME", tblName);
      tProps.put("PROPERTIES", properties.toString());
      tbl = createTableWithCapabilities(tProps);

      setHMSClient("testTransformerVirtualView", (new String[] { "HIVESQL" }));
      tbl2 = client.getTable(dbName, tblName);
      assertEquals("Table access type does not match the expected value:" + tblName,
          ACCESSTYPE_READONLY, tbl2.getAccessType()); // RO accessonly for all views

      setHMSClient("testTransformerVirtualView", (new String[] { "EXTWRITE", "HIVEFULLACIDWRITE" }));
      tbl2 = client.getTable(dbName, tblName);
      assertEquals("Table access type does not match the expected value:" + tblName,
          ACCESSTYPE_NONE, tbl2.getAccessType()); // missing HIVESQL

      LOG.info("Test execution complete:testTransformerVirtualView");
    } catch (Exception e) {
      fail("testTransformerVirtualView failed with " + e.getMessage());
    } finally {
      resetHMSClient();
    }
  }

};