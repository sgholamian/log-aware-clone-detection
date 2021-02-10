//,temp,TestRMNMInfo.java,66,86,temp,TestMRJobsWithHistoryService.java,80,99
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
      mrCluster = new MiniMRYarnCluster(TestRMNMInfo.class.getName(),
                                        NUMNODEMANAGERS);
      Configuration conf = new Configuration();
      mrCluster.init(conf);
      mrCluster.start();
    }

    // workaround the absent public distcache.
    localFs.copyFromLocalFile(new Path(MiniMRYarnCluster.APPJAR), APP_JAR);
    localFs.setPermission(APP_JAR, new FsPermission("700"));
  }

};