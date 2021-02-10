//,temp,TestMRJobsWithProfiler.java,74,93,temp,TestSpeculativeExecution.java,105,124
//,3
public class xxx {
  @BeforeClass
  public static void setup() throws InterruptedException, IOException {

    if (!(new File(MiniMRYarnCluster.APPJAR)).exists()) {
      LOG.info("MRAppJar " + MiniMRYarnCluster.APPJAR
               + " not found. Not running test.");
      return;
    }

    if (mrCluster == null) {
      mrCluster = new MiniMRYarnCluster(TestMRJobsWithProfiler.class.getName());
      mrCluster.init(CONF);
      mrCluster.start();
    }

    // Copy MRAppJar and make it private. TODO: FIXME. This is a hack to
    // workaround the absent public discache.
    localFs.copyFromLocalFile(new Path(MiniMRYarnCluster.APPJAR), APP_JAR);
    localFs.setPermission(APP_JAR, new FsPermission("700"));
  }

};