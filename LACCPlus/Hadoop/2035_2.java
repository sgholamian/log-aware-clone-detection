//,temp,TestMiniMRChildTask.java,350,373,temp,TestMROldApiJobs.java,65,88
//,3
public class xxx {
  @BeforeClass
  public static void setup()  throws IOException {

    if (!(new File(MiniMRYarnCluster.APPJAR)).exists()) {
      LOG.info("MRAppJar " + MiniMRYarnCluster.APPJAR
               + " not found. Not running test.");
      return;
    }

    if (mrCluster == null) {
      mrCluster = new MiniMRYarnCluster(TestMROldApiJobs.class.getName());
      mrCluster.init(new Configuration());
      mrCluster.start();
    }

    // TestMRJobs is for testing non-uberized operation only; see TestUberAM
    // for corresponding uberized tests.
    mrCluster.getConfig().setBoolean(MRJobConfig.JOB_UBERTASK_ENABLE, false);
    
    // Copy MRAppJar and make it private. TODO: FIXME. This is a hack to
    // workaround the absent public discache.
    localFs.copyFromLocalFile(new Path(MiniMRYarnCluster.APPJAR), TestMRJobs.APP_JAR);
    localFs.setPermission(TestMRJobs.APP_JAR, new FsPermission("700"));
  }

};