//,temp,DFSCIOTest.java,547,551,temp,YarnRegistryViewForProviders.java,220,227
//,3
public class xxx {
  private static void cleanup(FileSystem fs) throws Exception {
    LOG.info("Cleaning up test files");
    fs.delete(new Path(TEST_ROOT_DIR), true);
    fs.delete(HDFS_TEST_DIR, true);
  }

};