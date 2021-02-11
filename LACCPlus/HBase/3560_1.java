//,temp,TestMasterProcedureWalLease.java,85,92,temp,TestTableDDLProcedureBase.java,50,57
//,2
public class xxx {
  @After
  public void tearDown() throws Exception {
    try {
      UTIL.shutdownMiniCluster();
    } catch (Exception e) {
      LOG.warn("failure shutting down cluster", e);
    }
  }

};