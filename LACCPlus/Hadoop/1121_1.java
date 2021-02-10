//,temp,TestCheckpoint.java,2483,2491,temp,TestCheckpoint.java,2473,2481
//,2
public class xxx {
  private static void cleanup(MiniDFSCluster cluster) {
    if (cluster != null) {
      try {
        cluster.shutdown();
      } catch (Exception e) {
        LOG.warn("Could not shutdown MiniDFSCluster ", e);
      }
    }
  }

};