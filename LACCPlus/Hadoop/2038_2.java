//,temp,TestMRJobsWithProfiler.java,74,93,temp,TestMRAMWithNonNormalizedCapabilities.java,61,78
//,3
public class xxx {
  @Before
  public void setup() throws Exception {
    if (!(new File(MiniMRYarnCluster.APPJAR)).exists()) {
      LOG.info("MRAppJar " + MiniMRYarnCluster.APPJAR
        + " not found. Not running test.");
      return;
    }

    if (mrCluster == null) {
      mrCluster = new MiniMRYarnCluster(getClass().getSimpleName());
      mrCluster.init(new Configuration());
      mrCluster.start();
    }
    // Copy MRAppJar and make it private. TODO: FIXME. This is a hack to
    // workaround the absent public discache.
    localFs.copyFromLocalFile(new Path(MiniMRYarnCluster.APPJAR), APP_JAR);
    localFs.setPermission(APP_JAR, new FsPermission("700"));
  }

};