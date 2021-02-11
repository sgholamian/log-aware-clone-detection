//,temp,TestMRAppWithCombiner.java,67,88,temp,TestMRJobsWithHistoryService.java,80,99
//,3
public class xxx {
  @BeforeClass
  public static void setup() throws IOException {

    if (!(new File(MiniMRYarnCluster.APPJAR)).exists()) {
      LOG.info("MRAppJar " + MiniMRYarnCluster.APPJAR
          + " not found. Not running test.");
      return;
    }

    if (mrCluster == null) {
      mrCluster = new MiniMRYarnCluster(TestMRJobs.class.getName(), 3);
      Configuration conf = new Configuration();
      mrCluster.init(conf);
      mrCluster.start();
    }

    // Copy MRAppJar and make it private. TODO: FIXME. This is a hack to
    // workaround the absent public discache.
    localFs.copyFromLocalFile(new Path(MiniMRYarnCluster.APPJAR),
        TestMRJobs.APP_JAR);
    localFs.setPermission(TestMRJobs.APP_JAR, new FsPermission("700"));
  }

};