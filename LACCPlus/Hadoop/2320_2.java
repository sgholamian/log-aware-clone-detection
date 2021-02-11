//,temp,TestMiniMRChildTask.java,246,269,temp,TestMRAMWithNonNormalizedCapabilities.java,62,79
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