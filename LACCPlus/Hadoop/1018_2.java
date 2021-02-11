//,temp,TestMiniMRChildTask.java,350,373,temp,TestMRJobs.java,140,170
//,3
public class xxx {
  @BeforeClass
  public static void setup() throws IOException {
    try {
      dfsCluster = new MiniDFSCluster.Builder(conf).numDataNodes(2)
        .format(true).racks(null).build();
      remoteFs = dfsCluster.getFileSystem();
    } catch (IOException io) {
      throw new RuntimeException("problem starting mini dfs cluster", io);
    }

    if (!(new File(MiniMRYarnCluster.APPJAR)).exists()) {
      LOG.info("MRAppJar " + MiniMRYarnCluster.APPJAR
               + " not found. Not running test.");
      return;
    }

    if (mrCluster == null) {
      mrCluster = new MiniMRYarnCluster(TestMRJobs.class.getName(),
          NUM_NODE_MGRS);
      Configuration conf = new Configuration();
      conf.set("fs.defaultFS", remoteFs.getUri().toString());   // use HDFS
      conf.set(MRJobConfig.MR_AM_STAGING_DIR, "/apps_staging_dir");
      mrCluster.init(conf);
      mrCluster.start();
    }

    // Copy MRAppJar and make it private. TODO: FIXME. This is a hack to
    // workaround the absent public discache.
    localFs.copyFromLocalFile(new Path(MiniMRYarnCluster.APPJAR), APP_JAR);
    localFs.setPermission(APP_JAR, new FsPermission("700"));
  }

};