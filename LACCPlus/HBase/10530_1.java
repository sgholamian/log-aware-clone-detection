//,temp,TestRogueRSAssignment.java,114,123,temp,TestCreateNamespaceProcedure.java,68,75
//,3
public class xxx {
  @AfterClass
  public static void cleanupTest() throws Exception {
    try {
      UTIL.shutdownMiniCluster();
      cluster = null;
      admin = null;
    } catch (Exception e) {
      LOG.warn("failure shutting down cluster", e);
    }
  }

};