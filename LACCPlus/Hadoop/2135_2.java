//,temp,TestMRAMWithNonNormalizedCapabilities.java,109,120,temp,TestMRJobsWithHistoryService.java,101,112
//,2
public class xxx {
  @After
  public void tearDown() {
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