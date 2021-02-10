//,temp,TestMROldApiJobs.java,65,88,temp,TestSpeculativeExecution.java,105,124
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
      mrCluster = new MiniMRYarnCluster(TestSpeculativeExecution.class.getName(), 4);
      Configuration conf = new Configuration();
      mrCluster.init(conf);
      mrCluster.start();
    }

    // workaround the absent public distcache.
    localFs.copyFromLocalFile(new Path(MiniMRYarnCluster.APPJAR), APP_JAR);
    localFs.setPermission(APP_JAR, new FsPermission("700"));
  }

};