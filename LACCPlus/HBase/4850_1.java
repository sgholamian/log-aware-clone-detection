//,temp,TestModifyNamespaceProcedure.java,81,88,temp,TestRogueRSAssignment.java,131,139
//,3
public class xxx {
  @After
  public void tearDown() throws Exception {
    ProcedureTestingUtility.setKillAndToggleBeforeStoreUpdate(getMasterProcedureExecutor(), false);
    for (TableDescriptor htd: UTIL.getAdmin().listTableDescriptors()) {
      LOG.info("Tear down, remove table=" + htd.getTableName());
      UTIL.deleteTable(htd.getTableName());
    }
  }

};