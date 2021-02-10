//,temp,TestMRJobsWithProfiler.java,95,106,temp,TestMRJobsWithHistoryService.java,101,112
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