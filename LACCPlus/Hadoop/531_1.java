//,temp,TestMRJobsWithProfiler.java,95,106,temp,TestMRAMWithNonNormalizedCapabilities.java,109,120
//,2
public class xxx {
  @AfterClass
  public static void tearDown() {
    if (!(new File(MiniMRYarnCluster.APPJAR)).exists()) {
      LOG.info("MRAppJar " + MiniMRYarnCluster.APPJAR
          + " not found. Not running test.");
      return;
    }

    if (mrCluster != null) {
      mrCluster.stop();
    }
  }

};