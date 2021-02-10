//,temp,TestMRJobsWithProfiler.java,74,93,temp,TestMiniMRChildTask.java,350,373
//,3
public class xxx {
  @BeforeClass
  public static void setup() throws IOException {
    // create configuration, dfs, file system and mapred cluster 
    dfs = new MiniDFSCluster.Builder(conf).build();
    fileSys = dfs.getFileSystem();

    if (!(new File(MiniMRYarnCluster.APPJAR)).exists()) {
      LOG.info("MRAppJar " + MiniMRYarnCluster.APPJAR
          + " not found. Not running test.");
      return;
    }

    if (mr == null) {
      mr = new MiniMRYarnCluster(TestMiniMRChildTask.class.getName());
      Configuration conf = new Configuration();
      mr.init(conf);
      mr.start();
    }

    // Copy MRAppJar and make it private. TODO: FIXME. This is a hack to
    // workaround the absent public discache.
    localFs.copyFromLocalFile(new Path(MiniMRYarnCluster.APPJAR), APP_JAR);
    localFs.setPermission(APP_JAR, new FsPermission("700"));
  }

};