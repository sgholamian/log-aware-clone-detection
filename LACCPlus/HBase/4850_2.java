//,temp,TestModifyNamespaceProcedure.java,81,88,temp,TestRogueRSAssignment.java,131,139
//,3
public class xxx {
  @After
  public void tearDown() throws Exception {
    for (TableDescriptor td: UTIL.getAdmin().listTableDescriptors()) {
      LOG.info("Tear down, remove table=" + td.getTableName());
      UTIL.deleteTable(td.getTableName());
    }
    // Turn on balancer
    admin.setBalancerRunning(true, false);
  }

};