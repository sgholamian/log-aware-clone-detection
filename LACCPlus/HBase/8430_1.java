//,temp,TestMergeTableRegionsProcedure.java,122,129,temp,TestFromClientSide3.java,135,141
//,3
public class xxx {
  @After
  public void tearDown() throws Exception {
    resetProcExecutorTestingKillFlag();
    for (TableDescriptor htd: admin.listTableDescriptors()) {
      LOG.info("Tear down, remove table=" + htd.getTableName());
      UTIL.deleteTable(htd.getTableName());
    }
  }

};