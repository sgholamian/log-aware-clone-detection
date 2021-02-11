//,temp,TestMergeTableRegionsProcedure.java,122,129,temp,TestFromClientSide3.java,135,141
//,3
public class xxx {
  @After
  public void tearDown() throws Exception {
    for (HTableDescriptor htd: TEST_UTIL.getAdmin().listTables()) {
      LOG.info("Tear down, remove table=" + htd.getTableName());
      TEST_UTIL.deleteTable(htd.getTableName());
  }
  }

};