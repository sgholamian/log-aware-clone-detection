//,temp,TestCheckpoint.java,2483,2491,temp,TestCheckpoint.java,2473,2481
//,2
public class xxx {
  private static void cleanup(SecondaryNameNode snn) {
    if (snn != null) {
      try {
        snn.shutdown();
      } catch (Exception e) {
        LOG.warn("Could not shut down secondary namenode", e);
      }
    }
  }

};