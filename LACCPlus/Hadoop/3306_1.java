//,temp,TestDFSRollback.java,346,353,temp,TestDFSStartupVersions.java,282,289
//,2
public class xxx {
  @After
  public void tearDown() throws Exception {
    LOG.info("Shutting down MiniDFSCluster");
    if (cluster != null) {
      cluster.shutdown();
      cluster = null;
    }
  }

};